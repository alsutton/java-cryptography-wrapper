package com.enterprisepasswordsafe.cryptography;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class AsymmetricKeySupplier {
    private final AsymmetricKeyAlgorithmSupplier algorithmSupplier = new AsymmetricKeyAlgorithmSupplier();

    public KeyPair generateKeyPair()
            throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator kgen =
                KeyPairGenerator.getInstance(algorithmSupplier.getAlgorithm(), algorithmSupplier.getProvider());
        kgen.initialize(algorithmSupplier.getKeySize());
        return kgen.generateKeyPair();
    }

    public PublicKey convertToPublicKey(byte[] keyData)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
        if (keyData == null) {
            return null;
        }

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyData);
        KeyFactory factory = getKeyFactory();
        return factory.generatePublic(keySpec);
    }

    public PrivateKey convertToPrivateKey(byte[] keyData)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
        if (keyData == null) {
            return null;
        }

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyData);
        KeyFactory factory = getKeyFactory();
        return factory.generatePrivate(keySpec);
    }

    private KeyFactory getKeyFactory() throws NoSuchProviderException, NoSuchAlgorithmException {
        return KeyFactory.getInstance(algorithmSupplier.getAlgorithm(), algorithmSupplier.getProvider());
    }
}
