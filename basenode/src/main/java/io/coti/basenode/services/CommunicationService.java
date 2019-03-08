package io.coti.basenode.services;

import io.coti.basenode.communication.Channel;
import io.coti.basenode.communication.interfaces.IPropagationPublisher;
import io.coti.basenode.communication.interfaces.IPropagationSubscriber;
import io.coti.basenode.communication.interfaces.IReceiver;
import io.coti.basenode.communication.interfaces.ISender;
import io.coti.basenode.data.*;
import io.coti.basenode.data.interfaces.IPropagatable;
import io.coti.basenode.services.interfaces.IAddressService;
import io.coti.basenode.services.interfaces.ICommunicationService;
import io.coti.basenode.services.interfaces.IDspVoteService;
import io.coti.basenode.services.interfaces.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
public class CommunicationService implements ICommunicationService {
    @Autowired
    protected IReceiver receiver;
    @Autowired
    private IPropagationSubscriber propagationSubscriber;
    @Autowired
    private IPropagationPublisher propagationPublisher;
    @Autowired
    private ISender sender;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private ITransactionService transactionService;
    @Autowired
    private IDspVoteService dspVoteService;
    private EnumMap<NodeType, List<Class<? extends IPropagatable>>> publisherNodeTypeToMessageTypesMap;

    @Override
    public void initSubscriber(NodeType subscriberNodeType, EnumMap<NodeType, List<Class<? extends IPropagatable>>> publisherNodeTypeToMessageTypesMap) {
        propagationSubscriber.setSubscriberNodeType(subscriberNodeType);
        this.publisherNodeTypeToMessageTypesMap = publisherNodeTypeToMessageTypesMap;
        this.publisherNodeTypeToMessageTypesMap.putIfAbsent(NodeType.NodeManager, Arrays.asList(NetworkData.class));
        this.publisherNodeTypeToMessageTypesMap.putIfAbsent(NodeType.DspNode, Arrays.asList(TransactionData.class, AddressData.class));
        propagationSubscriber.setPublisherNodeTypeToMessageTypesMap(publisherNodeTypeToMessageTypesMap);
    }

    @Override
    public void initReceiver(String receivingPort, HashMap<String, Consumer<Object>> classNameToReceiverHandlerMapping) {
        receiver.init(receivingPort, classNameToReceiverHandlerMapping);
    }

    @Override
    public void addSender(String receivingServerAddress) {
        sender.connectToNode(receivingServerAddress);
    }

    @Override
    public void removeSender(String receivingFullAddress, NodeType nodeType) {
        sender.disconnectFromNode(receivingFullAddress, nodeType);
    }

    @Override
    public void addSubscription(String propagationServerAddress, NodeType publisherNodeType) {
        propagationSubscriber.connectAndSubscribeToServer(propagationServerAddress,publisherNodeType);
    }

    @Override
    public void removeSubscription(String propagationServerAddress, NodeType publisherNodeType) {
        propagationSubscriber.disconnect(propagationServerAddress, publisherNodeType);
    }

    @Override
    public void initPublisher(String propagationPort, NodeType propagatorType) {
        propagationPublisher.init(propagationPort, propagatorType);
    }
}