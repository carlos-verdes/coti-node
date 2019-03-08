package io.coti.trustscore.services;

import io.coti.basenode.crypto.NetworkNodeCrypto;
import io.coti.basenode.crypto.NodeCryptoHelper;
import io.coti.basenode.data.DspConsensusResult;
import io.coti.basenode.data.NetworkNodeData;
import io.coti.basenode.data.NodeType;
import io.coti.basenode.data.TransactionData;
import io.coti.basenode.data.interfaces.IPropagatable;
import io.coti.basenode.services.BaseNodeInitializationService;
import io.coti.basenode.services.interfaces.ICommunicationService;
import io.coti.basenode.services.interfaces.INetworkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

@Service
@Slf4j
public class InitializationService extends BaseNodeInitializationService {

    @Autowired
    private ICommunicationService communicationService;
    @Autowired
    private INetworkService networkService;
    @Value("${server.port}")
    private String serverPort;
    @Autowired
    private NetworkNodeCrypto networkNodeCrypto;
    private EnumMap<NodeType, List<Class<? extends IPropagatable>>> publisherNodeTypeToMessageTypesMap = new EnumMap<>(NodeType.class);

    @PostConstruct
    public void init() {
        super.initDB();
        super.connectToNetwork();

        publisherNodeTypeToMessageTypesMap.put(NodeType.ZeroSpendServer, Arrays.asList(TransactionData.class, DspConsensusResult.class));
        publisherNodeTypeToMessageTypesMap.put(NodeType.FinancialServer, Arrays.asList(TransactionData.class));

        communicationService.initSubscriber(NodeType.TrustScoreNode, publisherNodeTypeToMessageTypesMap);

        NetworkNodeData zerospendNetworkNodeData = networkService.getSingleNodeData(NodeType.ZeroSpendServer);
        if (zerospendNetworkNodeData == null) {
            log.error("Zero Spend server is down, info came from node manager");
            System.exit(-1);
        }
        networkService.setRecoveryServerAddress(zerospendNetworkNodeData.getHttpFullAddress());
        super.init();
        communicationService.addSubscription(zerospendNetworkNodeData.getPropagationFullAddress(), NodeType.ZeroSpendServer);
        List<NetworkNodeData> dspNetworkNodeData = networkService.getShuffledNetworkNodeDataListFromMapValues(NodeType.DspNode);
        dspNetworkNodeData.forEach(node -> communicationService.addSubscription(node.getPropagationFullAddress(), NodeType.DspNode));
    }

    @Override
    protected NetworkNodeData createNodeProperties() {
        NetworkNodeData networkNodeData = new NetworkNodeData(NodeType.TrustScoreNode, nodeIp, serverPort, NodeCryptoHelper.getNodeHash(), networkType);
        return networkNodeData;

    }
}