package io.coti.basenode.communication.data;

import io.coti.basenode.data.NodeType;
import lombok.Data;

import java.time.Instant;

@Data
public class ConnectedNodeData {
    NodeType nodeType;
    Instant lastConnectionTime;

    public ConnectedNodeData(NodeType nodeType, Instant lastConnectionTime) {
        this.nodeType = nodeType;
        this.lastConnectionTime = lastConnectionTime;
    }
}
