package net.mirjalal.mondash.encryption;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import net.mirjalal.mondash.configuration.EncryptorConfiguration;
import lombok.Setter;

@Setter
@Component
public class AesEncryptor implements Encryptor {

    private final Cipher encryptor;
    private final Cipher decryptor;

    private static Key getKey(String keyString) throws NoSuchAlgorithmException {
        byte[] key = keyString.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        return new SecretKeySpec(key, "AES");
    }

    public AesEncryptor(EncryptorConfiguration encryptorConfiguration) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        this.encryptor = Cipher.getInstance(AesEncryptorType.AES_ECB_PKS5PADDING.getName());
        this.encryptor.init(Cipher.ENCRYPT_MODE, getKey(encryptorConfiguration.getAesEncryptionKey()));

        this.decryptor = Cipher.getInstance(AesEncryptorType.AES_ECB_PKS5PADDING.getName());
        this.decryptor.init(Cipher.DECRYPT_MODE, getKey(encryptorConfiguration.getAesEncryptionKey()));

    }

    @Override
    public String encrypt(String text) {
        try {
            return Base64.getEncoder().encodeToString(this.encryptor.doFinal(text.getBytes(StandardCharsets.UTF_8)));
        } catch (BadPaddingException | IllegalBlockSizeException exception) {
            return null;
        }
    }

    @Override
    public String decrypt(String text) {
        try {
            return new String(this.decryptor.doFinal(Base64.getDecoder().decode(text)), StandardCharsets.UTF_8);
        } catch (BadPaddingException | IllegalBlockSizeException exception) {
            return null;
        }
    }
    
}
