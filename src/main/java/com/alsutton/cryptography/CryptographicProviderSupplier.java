package com.alsutton.cryptography;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;
import java.util.concurrent.atomic.AtomicBoolean;

public class CryptographicProviderSupplier {
    // Bouncy Castle cryptographic provider
    private static final String CRYPTOGRAPHIC_PROVIDER = "BC";

    private static AtomicBoolean initialised = new AtomicBoolean(Boolean.FALSE);

    public String getProvider() {
        ensureProviderIsInitialised();

        return CRYPTOGRAPHIC_PROVIDER;
    }

    private void ensureProviderIsInitialised() {
        if(initialised.getAndSet(Boolean.TRUE)) {
            return;
        }

        // Install the Bouncy Castle JCE Provider. This provides a number of
        // encryption algorithms which are not part of the standard JRE.
        // See section 6.1 in https://www.bouncycastle.org/specifications.html
        Security.addProvider(new BouncyCastleProvider());
    }
}
