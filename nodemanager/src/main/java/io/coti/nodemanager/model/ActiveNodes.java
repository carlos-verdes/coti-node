package io.coti.nodemanager.model;

import io.coti.basenode.model.Collection;
import io.coti.nodemanager.data.ActiveNodeData;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ActiveNodes extends Collection<ActiveNodeData> {

    private ActiveNodes() {
    }

    @PostConstruct
    public void init() {
        super.init();
    }
}
