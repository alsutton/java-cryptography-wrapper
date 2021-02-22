# java-cryptography-wrapper

This is a small library to simplify the API into the Java cryptography routines. 
It's designed to minimise the knowledge needed of the Java APIs so you can 
implement encryption and decryption with a small number of API calls.

There are some unit tests which provide examples of how to use the API provided. 
If you want to use this library for Public/Private key encryption (Asymmetric Encryption),
I would recommend using the [Two Level Encryption](https://github.com/alsutton/java-cryptography-wrapper/blob/main/src/test/java/com/enterprisepasswordsafe/cryptography/EncryptionRoundTripTests.java#L49)
method provided which encrypts the data with a unique symmetric key and then encrypts that
key using the private key of the key pair.

I offer no guarantees around the eternal security of the methods used, but they
form the basis of how the [Enterprise Passowrd Safe](https://github.com/alsutton/enterprisepasswordsafe)
works which has been in use by companies and government organisations for
over 15 years.