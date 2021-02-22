package com.enterprisepasswordsafe.cryptography;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class SymmetricCipherSupplier implements CipherSupplier {

    private static final SymmetricKeyAlgorithmSupplier algorithmSupplier = new SymmetricKeyAlgorithmSupplier();

    private static final String CIPHER_ALGORITHM = algorithmSupplier.getAlgorithm() + "/GCM/NoPadding";

    private byte[] ivBytes;

    SymmetricCipherSupplier(byte[] ivBytes) {
        this.ivBytes = ivBytes;
    }

    @Override
    public Cipher get(int mode, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, algorithmSupplier.getProvider());
            cipher.init(mode, key, new IvParameterSpec(ivBytes));
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException
                | InvalidAlgorithmParameterException | InvalidKeyException e) {
            // These exceptions mean we can't operate correctly, so the result should be fatal to the
            // application.
            throw new RuntimeException(e);
        }
    }

    public int getKeySizeInBytes() {
        return algorithmSupplier.getKeySize() / 8;
    }
}
