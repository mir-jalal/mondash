package net.mirjalal.mondash.encryption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.Test;

import net.mirjalal.mondash.configuration.EncryptorConfiguration;

class AesEncryptorTest {

    private AesEncryptor encryptor;

    public void setUp() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        EncryptorConfiguration encryptorConfiguration = new EncryptorConfiguration();
        encryptorConfiguration.setAesEncryptionKey("test-secret-key");
        encryptor = new AesEncryptor(encryptorConfiguration);
    }

    @Test
    void shouldEncryptAndDecryptCorrectly() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        this.setUp();
        String input = "important-data";

        String encrypted = encryptor.encrypt(input);
        String decrypted = encryptor.decrypt(encrypted);

        assertEquals(input, decrypted);
    }

    @Test
    void encryptShouldProduceDifferentOutputThanInput() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        this.setUp();
        String input = "data";

        assertNotEquals(input, encryptor.encrypt(input));
    }

    @Test
    void decryptWithWrongKeyShouldFail() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        this.setUp();
        EncryptorConfiguration wrongEncryptorConfiguration = new EncryptorConfiguration();
        wrongEncryptorConfiguration.setAesEncryptionKey("wrong-key");
        AesEncryptor other = new AesEncryptor(wrongEncryptorConfiguration);
        String input = "data";
        String encrypted = encryptor.encrypt(input);

        assertNotEquals(input, other.decrypt(encrypted));
    }
}
