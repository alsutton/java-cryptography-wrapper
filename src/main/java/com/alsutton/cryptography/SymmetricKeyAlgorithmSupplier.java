package com.alsutton.cryptography;

public class SymmetricKeyAlgorithmSupplier extends CryptographicProviderSupplier {

    private static final String SYMMETRIC_ALGORITHM = "AES";
    private static final int SYMMETRIC_KEY_SIZE_IN_BITS = 128;
    private static final int IV_SIZE_IN_BYTES = 12;

    public String getAlgorithm() {
        return SYMMETRIC_ALGORITHM;
    }

    public int getKeySize() {
        return SYMMETRIC_KEY_SIZE_IN_BITS;
    }

    public int getIVSizeInBytes() { return IV_SIZE_IN_BYTES; }
}
