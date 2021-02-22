package com.alsutton.cryptography;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class SymmetricKeySupplier {
    private SymmetricKeyAlgorithmSupplier algorithmSupplier = new SymmetricKeyAlgorithmSupplier();

    public SecretKey generateKey()
            throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator kgen =
                KeyGenerator.getInstance(algorithmSupplier.getAlgorithm(), algorithmSupplier.getProvider());
        kgen.init(algorithmSupplier.getKeySize());
        return kgen.generateKey();
    }

    public SecretKey convertToKey(byte[] keyData) {
        if (keyData == null) {
            return null;
        }

        return new SecretKeySpec(keyData, algorithmSupplier.getAlgorithm());
    }

    public int getKeySizeInBytes() {
        return algorithmSupplier.getKeySize() / 8;
    }

    public int getIVSizeInBytes() { return algorithmSupplier.getIVSizeInBytes(); }
}
