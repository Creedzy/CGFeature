

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnitRunner.class)
public class TestEncryptionService {

  
        private static final Logger logger = LoggerFactory.getLogger(TestEncryptionService.class);

        private Random random = new Random();

       
        EncryptionService encryptionService;
        
        
        @Test
        public void testEncryptDecryptData()  {
            logger.info("---------- testEncryptDecryptData ----------");

            for (int x = 0; x < 50; x++) {
                // Select a random plaintext length between 1 and 50
                int randomPlainTextLength = this.random.nextInt(50) + 1;
                // Generate a random plaintext
                String randomPlainText = RandomStringUtils.random(randomPlainTextLength, true, true);

                String encryptedPlainText = encryptionService.encryptPassword(randomPlainText);
                logger.debug("Test Number: {}, Random plaintext: {}", x, randomPlainText);
                logger.debug("Encrypted Plain Text: {}", encryptedPlainText);
                // Ciphertext should not be null
                assertNotNull(encryptedPlainText);
                // Ciphertext should not be equal to plaintext
                assertFalse(randomPlainText.equals(encryptedPlainText));
                // Decrypting the ciphertext should result in the original plaintext
                assertEquals(randomPlainText, encryptionService.decryptPassword(encryptedPlainText));
                String plainTextEncryptedASecondTime = encryptionService.encryptPassword(randomPlainText);
                // As a random IV is used, a plaintext encrypted with the same key should result in a different ciphertext every time it
                // is encrypted
                assertFalse(encryptedPlainText.equals(plainTextEncryptedASecondTime));
            }

        }


        @Test
        public void testEncryptDecryptWithSalt() {
            logger.info("---------- testEncryptDecryptWithSalt ----------");

            for (int x = 0; x < 50; x++) {
                // Select a random plaintext length between 1 and 50
                int randomPlainTextLength = this.random.nextInt(50) + 1;
                // Generate a random plaintext
                String randomPlainText = RandomStringUtils.random(randomPlainTextLength, true, true);

                // Encrypt plaintext without salt
                String encryptedPlainText = encryptionService.encryptPassword(randomPlainText);
                // Encrypt plaintext using salt
                String randomSalt = UUID.randomUUID().toString();
                String encryrptedPlainTextUsingSalt = encryptionService.encodeHash(randomPlainText, randomSalt);

                logger.trace("Test Number: {}, Random plaintext: {}", x, randomPlainText);
                logger.trace("Encrypted Plain Text: {}", encryptedPlainText);
                logger.trace("Encrypted Plain Text using Salt: {}", encryrptedPlainTextUsingSalt);

                // Salted encryption result should not be null
                assertNotNull(encryrptedPlainTextUsingSalt);
                // Encrypting with salt should result in different ciphertext than encrypting without salt
                assertFalse(encryptedPlainText.equals(encryrptedPlainTextUsingSalt));
                // Salted Encryption result should not equal the plaintext
                assertFalse(encryrptedPlainTextUsingSalt.equals(randomPlainText));
                // Salted Decryption of the Salted Encryption result should result in the original PlainText
                assertEquals(randomPlainText, encryptionService.decodeHash(encryrptedPlainTextUsingSalt, randomSalt));
            }

        }

        /**
         * Test encrypt a null value. EncryptionUtil should throw an A1EncryptionException
         * 
         */
        @Test(expected = EncryptionException.class)
        public void testEncryptNullValue() throws EncryptionException {
            logger.info("---------- testEncryptNullValue ----------");

            encryptionService.encryptPassword(null);

        }

        /**
         * Test decrypt a null value. EncryptionUtil should throw an A1EncryptionException
         *         
         */
        @Test(expected = EncryptionException.class)
        public void testDecryptNullValue() throws EncryptionException {
            logger.info("---------- testDecryptNullValue ----------");

            encryptionService.decryptPassword(null);
        }



        /**
         * Test encrypt an empty String.
         * 
         */
        @Test
        public void testEncryptDecryptEmptyString() throws EncryptionException {
            logger.info("---------- testEncryptDecryptEmptyString ----------");

            String encryptedEmptyString = encryptionService.encryptPassword("");

            logger.trace("Encrypted Empty String 1 : {}", encryptedEmptyString);
            // Encrypted Empty String should not be null and should not equal the plaintext (an empty String)
            assertNotNull(encryptedEmptyString);
            assertFalse("".equals(encryptedEmptyString));

            // Decrypting the ciphertext should result in the original plaintext
            assertEquals("", encryptionService.decryptPassword(encryptedEmptyString));

            String emptyStringEncryptedASecondTime = encryptionService.encryptPassword("");
            logger.trace("Encrypted Empty String 2 : {}", emptyStringEncryptedASecondTime);
            // As a random IV is used, an empty String encrypted with the same key should result in a different ciphertext every time it
            // is encrypted
            assertFalse(emptyStringEncryptedASecondTime.equals(encryptedEmptyString));
            assertEquals("", encryptionService.decryptPassword(emptyStringEncryptedASecondTime));
        }

      
}

    

