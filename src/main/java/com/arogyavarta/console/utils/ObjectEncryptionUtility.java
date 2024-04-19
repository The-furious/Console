package com.arogyavarta.console.utils;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.arogyavarta.console.config.Constants;

public class ObjectEncryptionUtility {

    private static final String ALGORITHM = Constants.DATA_ENCRYPTION_ALGO;
    private static final byte[] KEY = Constants.AES_KEY.getBytes();

    public static <T> void encryptStringFields(T object) throws Exception {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                String fieldValue = (String) field.get(object);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    String encryptedValue = encrypt(fieldValue);
                    field.set(object, encryptedValue);
                }
            }
        }
    }

    public static <T> void decryptStringFields(T object) throws Exception {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                String fieldValue = (String) field.get(object);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    String decryptedValue = decrypt(fieldValue);
                    field.set(object, decryptedValue);
                }
            }
        }
    }

    private static String encrypt(String input) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        Key secretKey = new SecretKeySpec(KEY, ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String decrypt(String encryptedInput) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        Key secretKey = new SecretKeySpec(KEY, ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedInput));
        return new String(decryptedBytes);
    }
}
