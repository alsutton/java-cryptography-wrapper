package com.alsutton.cryptography;

import java.security.PublicKey;

/**
 * Decrypter for use in asymmetric single block decryption
 */
public class AsymmetricSingleBlockDecrypter extends SingleRoundDecrypter {

    private static final AsymmetricCipherSupplier cipherSupplier = new AsymmetricCipherSupplier();

    public AsymmetricSingleBlockDecrypter(final PublicKey key) {
        super(cipherSupplier, key);
    }

    public int getBlockSize() {
        return cipherSupplier.getEncryptedBlockSize();
    }
}
