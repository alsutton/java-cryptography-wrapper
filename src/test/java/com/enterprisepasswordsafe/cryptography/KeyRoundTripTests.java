package com.enterprisepasswordsafe.cryptography;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests to verify keys created by once instance of a key supplier
 * can be recreated by another instance.
 */
public class KeyRoundTripTests {

    @Test
    public void testRoundTripForSymmetricKey()
            throws NoSuchProviderException, NoSuchAlgorithmException {
        SecretKey secretKey = new SymmetricKeySupplier().generateKey();

        byte[] encodedForm = secretKey.getEncoded();

        assertThat(new SymmetricKeySupplier().convertToKey(encodedForm))
                .isEqualTo(secretKey);
    }

    @Test
    public void testRoundTripForAsymmetricKeys()
            throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPair keys = new AsymmetricKeySupplier().generateKeyPair();

        byte[] encodedPrivateKey = keys.getPrivate().getEncoded();
        byte[] encodedPublicKey = keys.getPublic().getEncoded();

        assertThat(new AsymmetricKeySupplier().convertToPrivateKey(encodedPrivateKey))
                .isEqualTo(keys.getPrivate());
        assertThat(new AsymmetricKeySupplier().convertToPublicKey(encodedPublicKey))
                .isEqualTo(keys.getPublic());
    }

}
