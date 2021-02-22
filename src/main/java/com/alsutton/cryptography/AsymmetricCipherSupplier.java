package com.alsutton.cryptography;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class AsymmetricCipherSupplier implements CipherSupplier {

    private static final AsymmetricKeyAlgorithmSupplier algorithmSupplier = new AsymmetricKeyAlgorithmSupplier();

    @Override
    public Cipher get(int mode, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(algorithmSupplier.getAlgorithm(), algorithmSupplier.getProvider());
            cipher.init(mode, key);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException | InvalidKeyException e) {
            // These exceptions mean we can't operate correctly, so the result should be fatal to the
            // application.
            throw new RuntimeException(e);
        }
    }

    public int getEncryptedBlockSize() {
        return algorithmSupplier.getKeySize() / 8;
    }
}
