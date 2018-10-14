package io.coti.trustscore.data.Buckets;

import io.coti.basenode.data.Hash;
import io.coti.trustscore.data.Events.EventData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Data
public abstract class BucketEventData<T extends EventData> implements Serializable {

    protected Date StartPeriodTime;
    protected double CalculatedDelta;
    protected Date LastDateCalculated;
    HashMap<Hash, EventData> bucketEvents;


    public BucketEventData() {
        bucketEvents = new LinkedHashMap<>();
        StartPeriodTime = new Date();
        CalculatedDelta = 0;
    }

    protected abstract int bucketPeriodTime();

    public boolean isEventExistsInBucket(T eventData) {
        return (bucketEvents.containsKey(eventData.getHash()));
    }

    public void addEventToBucket(T eventData) {
        if (isEventExistsInBucket(eventData)) return;
        addEventToCalculations(eventData);

        //TODO: if we have a problem here, event can be added without calculated
        bucketEvents.put(eventData.getUniqueIdentifier(), eventData);


    }

    protected abstract void addEventToCalculations(T eventData);

    public double getBucketEventAddition() {
        return CalculatedDelta;
    }


}

