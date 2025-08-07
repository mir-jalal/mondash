package net.mirjalal.mondash.encryption;

public interface Encryptor {
    String encrypt(String text);
    String decrypt(String text);
}
