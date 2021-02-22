package com.enterprisepasswordsafe.cryptography;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static com.google.common.truth.Truth.assertThat;

public class EncryptionRoundTripTests {

    private String TEST_STRING = "TESTtestTest";
    private byte[] TEST_DATA = TEST_STRING.getBytes(StandardCharsets.UTF_16);

    @Test
    public void testAsymmetricRoundTrip()
            throws NoSuchAlgorithmException, BadPaddingException, InvalidKeyException,
            IllegalBlockSizeException, NoSuchProviderException {
        AsymmetricKeySupplier keySupplier = new AsymmetricKeySupplier();
        KeyPair keyPair = keySupplier.generateKeyPair();

        AsymmetricSingleBlockEncrypter encrypter = new AsymmetricSingleBlockEncrypter(keyPair.getPrivate());
        AsymmetricSingleBlockDecrypter decrypter = new AsymmetricSingleBlockDecrypter(keyPair.getPublic());

        roundTrip(encrypter, decrypter);
    }

    @Test
    public void testSymmetricRoundTrip()
            throws NoSuchAlgorithmException, BadPaddingException, InvalidKeyException,
            IllegalBlockSizeException, NoSuchProviderException {
        SymmetricKeySupplier keySupplier = new SymmetricKeySupplier();
        SecretKey secretKey = keySupplier.generateKey();

        byte[] iv = new byte[keySupplier.getIVSizeInBytes()];
        SymmetricEncrypter encrypter = new SymmetricEncrypter(secretKey, iv);
        SymmetricDecrypter decrypter = new SymmetricDecrypter(secretKey, iv);

        roundTrip(encrypter, decrypter);
    }

    @Test
    public void testTwoLevelRoundTrip()
            throws NoSuchAlgorithmException, BadPaddingException, InvalidKeyException,
            IllegalBlockSizeException, NoSuchProviderException {
        AsymmetricKeySupplier keySupplier = new AsymmetricKeySupplier();
        KeyPair keyPair = keySupplier.generateKeyPair();

        TwoLevelEncrypter encrypter = new TwoLevelEncrypter(keyPair.getPrivate());
        TwoLevelDecrypter decrypter = new TwoLevelDecrypter(keyPair.getPublic());

        roundTrip(encrypter, decrypter);
    }

    private void roundTrip(Encrypter encrypter, Decrypter decrypter)
            throws IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException,
            BadPaddingException, NoSuchProviderException {
        byte[] encrypted = encrypter.apply(TEST_DATA);
        assertThat(encrypted).isNotEqualTo(TEST_DATA);

        byte[] decrypted = decrypter.apply(encrypted);
        assertThat(decrypted).isEqualTo(TEST_DATA);
    }
}
