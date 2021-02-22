package com.alsutton.cryptography;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.PublicKey;

public class TwoLevelDecrypter implements Decrypter {
    private final AsymmetricSingleBlockDecrypter asymmetricDecrypter;

    private final SymmetricKeySupplier symmetricKeySupplier = new SymmetricKeySupplier();

    public TwoLevelDecrypter(PublicKey asymmetricDecryptionKey)
        throws InvalidKeyException {
        if (asymmetricDecryptionKey == null) {
            throw new InvalidKeyException("Attempt to decrypt without appropriate key.");
        }

        asymmetricDecrypter = new AsymmetricSingleBlockDecrypter(asymmetricDecryptionKey);
    }

    public final String decryptToString(final byte[] data)
            throws GeneralSecurityException {
        if( data == null) {
            return null;
        }

        return new String(apply(data), StandardCharsets.UTF_16);
    }

    @Override
    public final byte[] apply(final byte[] data)
            throws BadPaddingException, IllegalBlockSizeException {
        if (data == null) {
            return null;
        }

        SecretKey secretKey = extractSymmetricKey(data);
        byte[] ivData = extractIV(data);
        SymmetricDecrypter symmetricDecrypter = new SymmetricDecrypter(secretKey, ivData);
        int headerSize = asymmetricDecrypter.getBlockSize() * 2;
        return symmetricDecrypter.apply(data, headerSize, data.length - headerSize);
    }

    private SecretKey extractSymmetricKey(byte[] data)
            throws BadPaddingException, IllegalBlockSizeException {
        // Extract the second level key and decode it.
        byte[] keyData = asymmetricDecrypter.apply(data, 0, asymmetricDecrypter.getBlockSize());

        // This is needed because the older bouncy castle providers
        // drops leading zeros which caused compatibility issues.
        int encryptionKeySizeByes =  symmetricKeySupplier.getKeySizeInBytes();
        int missingBytes = encryptionKeySizeByes - keyData.length;
        if (missingBytes != 0) {
            byte[] oldkeyData = keyData;
            keyData = new byte[encryptionKeySizeByes];
            int i = 0;
            for (; i < missingBytes; i++) {
                keyData[i] = 0;
            }
            System.arraycopy(oldkeyData, 0, keyData, i, oldkeyData.length);
        }

        return symmetricKeySupplier.convertToKey(keyData);
    }

    private byte[] extractIV(byte[] data) throws BadPaddingException, IllegalBlockSizeException {
        return asymmetricDecrypter.apply(data, asymmetricDecrypter.getBlockSize(), asymmetricDecrypter.getBlockSize());
    }
}
