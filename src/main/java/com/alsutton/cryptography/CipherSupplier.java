package com.alsutton.cryptography;

import javax.crypto.Cipher;
import java.security.Key;

public interface CipherSupplier {
    Cipher get(int mode, Key key);
}
