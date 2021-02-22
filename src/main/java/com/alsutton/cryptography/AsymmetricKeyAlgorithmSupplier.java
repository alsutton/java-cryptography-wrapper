package com.alsutton.cryptography;

public class AsymmetricKeyAlgorithmSupplier extends CryptographicProviderSupplier {

    private static final String ASYMMETRIC_ALGORITHM = "RSA";
    private static final int ASYMMETRIC_KEY_SIZE_IN_BITS = 1024;

    public String getAlgorithm() {
        return ASYMMETRIC_ALGORITHM;
    }

    public int getKeySize() {
        return ASYMMETRIC_KEY_SIZE_IN_BITS;
    }
}
