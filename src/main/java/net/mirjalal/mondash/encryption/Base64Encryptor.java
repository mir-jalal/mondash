package net.mirjalal.mondash.encryption;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class Base64Encryptor implements Encryptor {

    @Override
    public String encrypt(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String decrypt(String text) {
        return new String(Base64.getDecoder().decode(text.getBytes(StandardCharsets.UTF_8)));
    }
}
