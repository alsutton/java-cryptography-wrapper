package com.alsutton.cryptography;

import java.security.PrivateKey;

/**
 * Encrypter for use in asymmetric single block encryption
 */
public class AsymmetricSingleBlockEncrypter extends SingleRoundEncrypter {

    private final static AsymmetricCipherSupplier cipherSupplier = new AsymmetricCipherSupplier();

    public AsymmetricSingleBlockEncrypter(final PrivateKey key) {
        super(cipherSupplier, key);
    }
}
