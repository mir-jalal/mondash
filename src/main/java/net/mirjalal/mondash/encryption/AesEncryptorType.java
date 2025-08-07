package net.mirjalal.mondash.encryption;

public enum AesEncryptorType {
    AES_ECB_PKS5PADDING("AES/ECB/PKCS5PADDING");

    private final String name;

    AesEncryptorType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
