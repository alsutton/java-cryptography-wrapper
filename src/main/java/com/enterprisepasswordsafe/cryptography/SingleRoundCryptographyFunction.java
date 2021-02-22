package com.enterprisepasswordsafe.cryptography;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.Key;

public abstract class SingleRoundCryptographyFunction {

    private final CipherSupplier cipherSupplier;

    private final Key key;

    SingleRoundCryptographyFunction(CipherSupplier cipherSupplier, Key key) {
        this.cipherSupplier = cipherSupplier;
        this.key = key;
    }

    abstract int getCipherMode();

    public byte[] apply(byte[] bytes)
            throws BadPaddingException, IllegalBlockSizeException {
        return apply(bytes, 0, bytes.length);
    }

    public byte[] apply(byte[] bytes, int start, int length)
            throws BadPaddingException, IllegalBlockSizeException {
        if (bytes == null) {
            return null;
        }

        Cipher cipher = cipherSupplier.get(getCipherMode(), key);
        return cipher.doFinal(bytes, start, length);
    }
}
