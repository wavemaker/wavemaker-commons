package com.wavemaker.commons.crypto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CryptoUtilsTest {

    @Test
    public void testEncryption() {
        String secretKey = "abcdefghijklmnop";

        String message = "the secret message";

        String cipherText = CryptoUtils.encrypt(secretKey, message);
        String decrypted = CryptoUtils.decrypt(secretKey, cipherText);
        assertEquals(message, decrypted);
    }
}