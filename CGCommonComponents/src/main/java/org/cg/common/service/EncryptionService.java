package org.cg.common.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface EncryptionService {
    
    public String encryptPassword(String password);
    public String decryptPassword(String encryptedPassword);
    public String encodeHash(String password, String hash);
    public String decodeHash(String password, String encodedHash);

}
