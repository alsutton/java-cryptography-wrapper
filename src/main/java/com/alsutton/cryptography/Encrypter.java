package com.alsutton.cryptography;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Interface allowing us to use type safety to ensure that decryption
 * operations are serviced by an appropriate class.
 */
public interface Encrypter {
    /**
     * Encrypt the supplied data.
     *
     * @param data The data to encrypt.
     * @return The encrypted representation.
     */
    byte[] apply(byte[] data)
            throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException,
                    NoSuchAlgorithmException, NoSuchProviderException;
}
