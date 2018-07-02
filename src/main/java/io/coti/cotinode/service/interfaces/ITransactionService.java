package io.coti.cotinode.service.interfaces;

import io.coti.cotinode.data.Hash;
import io.coti.cotinode.data.TransactionData;
import io.coti.cotinode.http.AddTransactionRequest;
import io.coti.cotinode.http.AddTransactionResponse;
import io.coti.cotinode.http.GetTransactionResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ITransactionService {

    ResponseEntity<AddTransactionResponse> addNewTransaction(AddTransactionRequest request);

    TransactionData getTransactionData(Hash transactionHash);

    ResponseEntity<GetTransactionResponse> getTransactionDetails(Hash transactionHash);

    ResponseEntity<AddTransactionResponse> addTransactionFromPropagation(AddTransactionRequest request);
}
