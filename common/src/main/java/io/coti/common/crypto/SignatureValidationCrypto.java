package io.coti.common.crypto;

import io.coti.common.data.interfaces.ISignValidatable;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public abstract class SignatureValidationCrypto<T extends ISignValidatable>{
    public abstract byte[] getMessageInBytes(T signValidatable);

    public boolean verifySignature(T signValidatable) {
        try {
            return CryptoHelper.VerifyByPublicKey(this.getMessageInBytes(signValidatable), signValidatable.getSignature().getR(), signValidatable.getSignature().getS(), signValidatable.getSignerHash().toHexString());
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
}
