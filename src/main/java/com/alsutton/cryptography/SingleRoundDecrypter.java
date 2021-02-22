package com.alsutton.cryptography;

import javax.crypto.Cipher;
import java.security.Key;

public abstract class SingleRoundDecrypter extends SingleRoundCryptographyFunction implements Decrypter {
    SingleRoundDecrypter(CipherSupplier cipherSupplier, Key key) {
        super(cipherSupplier, key);
    }

    @Override
    int getCipherMode() {
        return Cipher.DECRYPT_MODE;
    }
}
