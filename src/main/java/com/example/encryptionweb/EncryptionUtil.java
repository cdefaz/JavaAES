package com.example.encryptionweb;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;


public final class EncryptionUtil {
    // AES defaults to AES/ECB/PKCS5Padding in Java 7 -> Fidelity requires AES/CBC/PKCS5Padding
    public static final String encryptionType = "AES/CBC/PKCS5Padding";
    public static final String algorithm = "AES";
    public static final int bitSize = 128;
    // Must be 16 characters long -> this should be moved to a separate file once in Fidelity system
    public static final String initVector = "sdio*||auytnxeyu";

    //returns encoded text in hex format
    @NotNull
    public static String keyToString(SecretKey keyToEncode){
        byte[] data = keyToEncode.getEncoded();
        return Hex.encodeHexString(data);
    }
    //returns key in hex format
    @NotNull
    public static String textToString(byte[] encodedTextBytes){
        return Hex.encodeHexString(encodedTextBytes);
    }

    //generates and returns a secret key
    private static SecretKey getSecretEncryptionKey() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance(algorithm);
        generator.init(bitSize); // The AES key size in number of bits
        return generator.generateKey();
    }

    // generates and returns an initialization vector from the given string
    private static IvParameterSpec getIvParameter() throws Exception{
        return new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
    }

    // decrypts text given the encrypted string and secret key as Strings
    public static String decryptAES(String encryptedText, String secKeyString) throws Exception {
        byte[] textAsBytes = Hex.decodeHex(encryptedText);
        byte[] decodedKey = Hex.decodeHex(secKeyString);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);
        IvParameterSpec ivKey = getIvParameter();
        Cipher aesCipher = Cipher.getInstance(encryptionType);
        aesCipher.init(Cipher.DECRYPT_MODE, originalKey,ivKey);
        byte[] bytePlainText = aesCipher.doFinal(textAsBytes);
        return new String(bytePlainText);
    }

    // encrypts a given string and returns an encryption object that contains the text and key
    public static Encryption encryptAES(String plainText) throws Exception{
        SecretKey secKey = getSecretEncryptionKey();
        IvParameterSpec ivKey = getIvParameter();
        Cipher aesCipher = Cipher.getInstance(encryptionType);
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey,ivKey);
        byte[] data = secKey.getEncoded();
        byte[] encryptedText = aesCipher.doFinal(plainText.getBytes());
        return new Encryption(Hex.encodeHexString(encryptedText), Hex.encodeHexString(data));
    }
}
