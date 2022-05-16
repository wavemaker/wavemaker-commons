package com.wavemaker.commons.crypto;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.wavemaker.commons.WMRuntimeException;

public class CryptoUtils {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private static final String ENCRYPTION_ALG = "AES/GCM/NoPadding";

    private static final String SECRET_KEY_SPEC_ALGORITHM = "AES";
    private static final int GCM_IV_LENGTH = 12;

    private CryptoUtils() {
    }

    public static String encrypt(String key, String plaintext) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), SECRET_KEY_SPEC_ALGORITHM);
        byte[] iv = new byte[GCM_IV_LENGTH];
        SECURE_RANDOM.nextBytes(iv);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALG);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, parameterSpec);

            byte[] cipherText = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
            byteBuffer.put(iv);
            byteBuffer.put(cipherText);
            return Base64.getEncoder().encodeToString(byteBuffer.array());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new WMRuntimeException(e);
        }
    }

    public static String decrypt(String key, String encryptedText) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), SECRET_KEY_SPEC_ALGORITHM);
        byte[] cipherMessage = Base64.getDecoder().decode(encryptedText);
        AlgorithmParameterSpec gcmParameterSpec = new GCMParameterSpec(128, cipherMessage, 0, GCM_IV_LENGTH);
        try {
            final Cipher cipher = Cipher.getInstance(ENCRYPTION_ALG);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
            byte[] plainText = cipher.doFinal(cipherMessage, GCM_IV_LENGTH, cipherMessage.length - GCM_IV_LENGTH);
            return new String(plainText, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new WMRuntimeException(e);
        }
    }
}