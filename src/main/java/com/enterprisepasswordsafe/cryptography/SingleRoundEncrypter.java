package com.enterprisepasswordsafe.cryptography;

import javax.crypto.Cipher;
import java.security.Key;
import java.util.function.Supplier;

public abstract class SingleRoundEncrypter extends SingleRoundCryptographyFunction implements Encrypter {
    SingleRoundEncrypter(CipherSupplier cipherSupplier, Key key) {
        super(cipherSupplier, key);
    }

    @Override
    int getCipherMode() {
        return Cipher.ENCRYPT_MODE;
    }
}
