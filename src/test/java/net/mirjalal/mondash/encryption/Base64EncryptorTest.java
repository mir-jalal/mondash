package net.mirjalal.mondash.encryption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class Base64EncryptorTest {

    private final Base64Encryptor encryptor = new Base64Encryptor();

    @Test
    void shouldEncryptAndDecryptSymmetrically() {
        String input = "secret-value";

        String encrypted = encryptor.encrypt(input);
        String decrypted = encryptor.decrypt(encrypted);

        assertEquals(input, decrypted);
    }

    @Test
    void encryptShouldNotReturnPlainText() {
        String input = "plain";

        String encrypted = encryptor.encrypt(input);

        assertNotEquals(input, encrypted);
    }

    @Test
    void decryptInvalidValueShouldThrowException() {
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> encryptor.decrypt("###invalid###"));
        assertEquals("Illegal base64 character 23", ex.getMessage());
    }
}
