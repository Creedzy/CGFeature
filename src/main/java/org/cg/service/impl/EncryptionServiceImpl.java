package org.cg.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.cg.config.ConfigurationService;
import org.cg.service.EncryptionService;
import org.cg.service.Exception.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService{
    Logger logger = LoggerFactory.getLogger(EncryptionServiceImpl.class);
    
    ConfigurationService config;
    
    private SecretKeySpec secretKey;
    
    private char[] secretKeyChar;

    private SecureRandom secureRandom = new SecureRandom();
    
    Cipher cipher;
    
    @Autowired
    public EncryptionServiceImpl(ConfigurationService config_) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.config = config_;
        secretKey = new SecretKeySpec(config.getEncryptionKey().getBytes(),"AES");
        secretKeyChar = config.getEncryptionKey().toCharArray();
        cipher = Cipher.getInstance("AES");
    }
    
    @Override
    public String encryptPassword(String password)  {
        byte[] randomInitializationVector = new byte[16];
        this.secureRandom.nextBytes(randomInitializationVector);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(randomInitializationVector);
        
        
        
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, ivParameterSpec);
            byte[] cipherText = cipher.doFinal(password.getBytes());  
            byte[] cipherTextAndIV = 
                            ArrayUtils.addAll(randomInitializationVector, cipherText);
            String result = Base64.encodeBase64String(cipherTextAndIV);
            return result;
        }
        catch (NullPointerException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
            logger.debug("Error:{}",e);
            throw new EncryptionException(e.getMessage());
        }       
    }

    @Override
    public String decryptPassword(String encryptedPassword) {
        try {
        byte[] cipherTextAndIV = Base64.decodeBase64(encryptedPassword);
        byte[] iv = Arrays.copyOfRange(cipherTextAndIV, 0, 16);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        byte[] cipherText = Arrays.copyOfRange(cipherTextAndIV, 16, cipherTextAndIV.length);
       
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, this.secretKey, ivParameterSpec);
            return new String(cipher.doFinal(cipherText));
        }
        catch ( NullPointerException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
            logger.debug("Error:{}",e);
            throw new EncryptionException(e.getMessage());
        }
        
        
    }

    @Override
    public String encodeHash(String password,String hash) {
        // Hash the key with the salt before encryption
        try {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey secretKeyWithSalt = factory
                        .generateSecret(new PBEKeySpec(this.secretKeyChar, hash.getBytes(), 4096, 128));
        byte[] secretKeyWithSaltBytes = Arrays.copyOf(secretKeyWithSalt.getEncoded(), 16);
        SecretKeySpec secretKeyHashedWithSalt = new SecretKeySpec(secretKeyWithSaltBytes, "AES");
        
        // Generate a random IV
        byte[] randomInitializationVector = new byte[16];
        this.secureRandom.nextBytes(randomInitializationVector);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(randomInitializationVector);

        // Encrypt the plainText using the secret key (hashed with salt) and random IV using AES CBC Mode
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeyHashedWithSalt, ivParameterSpec);
        byte[] cipherText = cipher.doFinal(password.getBytes("UTF-8"));

        // Combine the IV and cipherText in the result
        byte[] cipherTextAndIV = ArrayUtils.addAll(randomInitializationVector, cipherText);
        String result = Base64.encodeBase64String(cipherTextAndIV);
        return result; 
        } catch (NullPointerException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException 
                 | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            logger.debug("Error:{}",e);
            throw new EncryptionException(e.getMessage());
        }
    }

    @Override
    public String decodeHash(String encodedPassword,String hash) {
        // Hash the key with the salt before decryption
        try{
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey secretKeyWithSalt = factory
                        .generateSecret(new PBEKeySpec(this.secretKeyChar, hash.getBytes(), 4096, 128));
        byte[] secretKeyWithSaltBytes = Arrays.copyOf(secretKeyWithSalt.getEncoded(), 16);
        SecretKeySpec secretKeyHashedWithSalt = new SecretKeySpec(secretKeyWithSaltBytes, "AES");

        // The first 16 bytes of the cipherText are the random IV
        byte[] cipherTextAndIV = Base64.decodeBase64(encodedPassword);
        byte[] iv = Arrays.copyOfRange(cipherTextAndIV, 0, 16);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Use the IV and secret key (hashed with salt) to decrypt the ciphertext to the original plaintext
        byte[] cipherText = Arrays.copyOfRange(cipherTextAndIV, 16, cipherTextAndIV.length);
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeyHashedWithSalt, ivParameterSpec);
        return new String(cipher.doFinal(cipherText));
        } catch(NullPointerException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException 
               | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            logger.debug("Error:{}",e);
            throw new EncryptionException(e.getMessage());
        }
    }
    
}
