package net.mirjalal.mondash.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "app.gitlab")
@Getter
@Setter
public class GitlabConfiguration {
    private String token;
    private String jobUrl;
}
