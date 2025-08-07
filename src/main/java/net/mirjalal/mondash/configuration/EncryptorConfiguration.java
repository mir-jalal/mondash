package net.mirjalal.mondash.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.encryption")
@Configuration
public class EncryptorConfiguration {
    private String aesEncryptionKey;
}
