package io.coti.common.http;

import io.coti.common.http.data.TrustScoreResponseData;
import lombok.Data;

@Data
public class GetTransactionTrustScoreResponse extends Response {
    private TrustScoreResponseData trustScoreResponseData;

    public GetTransactionTrustScoreResponse(TrustScoreResponseData trustScoreResponseData) {
        this.trustScoreResponseData = trustScoreResponseData;
    }
}