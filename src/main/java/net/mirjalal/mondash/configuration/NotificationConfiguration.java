package net.mirjalal.mondash.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "app.notification")
@Getter
@Setter
public class NotificationConfiguration {

    private Ssl ssl;
    private Matrix matrix;

    @Setter
    @Getter
    public static class Ssl {
        private String bundle;
    }

    @Setter
    @Getter
    public static class Matrix {
        private String url;
        private String room;
        private String token;
    }
}
