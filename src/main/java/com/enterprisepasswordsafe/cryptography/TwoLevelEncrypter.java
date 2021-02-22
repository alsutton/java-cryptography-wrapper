package com.enterprisepasswordsafe.cryptography;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;

/**
 * Perform two level encryption on some data, the inner layer is a symmetric cipher, the outer
 * layer is an asymmetric cipher where the key is used for access control.
 */
public final class TwoLevelEncrypter implements Encrypter {

    private final AsymmetricSingleBlockEncrypter asymmetricEncrypter;

    private final SymmetricKeySupplier symmetricKeySupplier = new SymmetricKeySupplier();

    public TwoLevelEncrypter(PrivateKey asymmetricEncryptionKey)
        throws InvalidKeyException {
        if (asymmetricEncryptionKey == null) {
            throw new InvalidKeyException("Attempt to decrypt without appropriate key.");
        }

        asymmetricEncrypter = new AsymmetricSingleBlockEncrypter(asymmetricEncryptionKey);
    }

    public final byte[] encrypt(final String data)
            throws GeneralSecurityException {
        if( data == null ) {
            return null;
        }
        return apply(data.getBytes(StandardCharsets.UTF_16));
    }

    @Override
    public final byte[] apply(final byte[] data)
            throws NoSuchAlgorithmException, BadPaddingException,
                    IllegalBlockSizeException, NoSuchProviderException {
        // Encrypt the symmetric key
        SecretKey secondLevelKey = symmetricKeySupplier.generateKey();
        byte[] encryptedKey = asymmetricEncrypter.apply(secondLevelKey.getEncoded());

        // Generate an initialisation vector
        byte[] initialisationVector = new byte[symmetricKeySupplier.getIVSizeInBytes()];
        new SecureRandom().nextBytes(initialisationVector);
        byte[] encryptedIV = asymmetricEncrypter.apply(initialisationVector);

        // Encrypt the data.
        SymmetricEncrypter symmetricEncrypter = new SymmetricEncrypter(secondLevelKey, initialisationVector);
        byte[] encryptedData = symmetricEncrypter.apply(data);

        // Combine the two blocks into one
        byte[] finalData = new byte[encryptedKey.length + encryptedIV.length + encryptedData.length];
        System.arraycopy(encryptedKey, 0,
                finalData, 0, encryptedKey.length);
        System.arraycopy(encryptedIV, 0,
                finalData, encryptedKey.length, encryptedIV.length);
        System.arraycopy(encryptedData, 0,
                finalData,encryptedKey.length + encryptedIV.length, encryptedData.length);

        return finalData;
    }
}
