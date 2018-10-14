package io.coti.trustscore.database;

import io.coti.basenode.database.RocksDBConnector;
import io.coti.trustscore.model.TransactionEvents;
import io.coti.trustscore.model.TrustScores;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@Slf4j
public class TrustScoreRocksDBConnector extends RocksDBConnector {


    public TrustScoreRocksDBConnector() {
        addTrustScoreColumnFamilies();
    }


    private void addTrustScoreColumnFamilies() {
        columnFamilyClassNames.add(TransactionEvents.class.getName());
        columnFamilyClassNames.add(TrustScores.class.getName());
    }

}