package io.coti.trustscore.http;

import io.coti.basenode.data.Hash;
import io.coti.basenode.http.Request;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetRollingReserveRequest extends Request {

    @NotNull
    private Hash userHash;
    @NotNull
    private double amount;

}
