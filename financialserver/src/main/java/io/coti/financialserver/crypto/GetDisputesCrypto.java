package io.coti.financialserver.crypto;

import io.coti.basenode.crypto.CryptoHelper;
import io.coti.basenode.crypto.SignatureCrypto;
import io.coti.financialserver.http.data.GetDisputesData;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
public class GetDisputesCrypto extends SignatureCrypto<GetDisputesData> {

    @Override
    public byte[] getMessageInBytes(GetDisputesData getDisputesData) {

        byte [] disputeHashesInBytes = getDisputesData.getDisputeHashes()!= null ? getDisputesData.getDisputeHashes().toString().getBytes() : new byte[0];
        byte [] disputeSideInBytes = getDisputesData.getDisputeSide().toString().getBytes();
        int byteBufferLength = disputeHashesInBytes.length + disputeSideInBytes.length;

        ByteBuffer disputeDataBuffer = ByteBuffer.allocate(byteBufferLength).put(disputeHashesInBytes).put(disputeSideInBytes);

        byte[] disputeDataInBytes = disputeDataBuffer.array();
        return CryptoHelper.cryptoHash(disputeDataInBytes).getBytes();
    }
}