package com.enterprisepasswordsafe.cryptography;

import javax.crypto.SecretKey;

/**
 * Decrypter for use in symmetric decryption
 */
public class SymmetricDecrypter extends SingleRoundDecrypter {

    public SymmetricDecrypter(final SecretKey key, byte[] iv) {
        super(new SymmetricCipherSupplier(iv), key);
    }
}
