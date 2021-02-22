package com.alsutton.cryptography;

import javax.crypto.SecretKey;

/**
 * Encrypter for use in symmetric encryption
 */
public class SymmetricEncrypter extends SingleRoundEncrypter {
    public SymmetricEncrypter(final SecretKey key, byte[] iv) {
        super(new SymmetricCipherSupplier(iv), key);
    }
}
