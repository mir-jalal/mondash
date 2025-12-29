package net.mirjalal.mondash.notification;

import java.util.HashMap;

import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientSsl;
import org.springframework.web.reactive.function.client.WebClient;

import net.mirjalal.mondash.configuration.NotificationConfiguration;
import net.mirjalal.mondash.model.Alert;

public class MatrixNotification implements NotificationStrategy {

    private final WebClient webClient;
    private final String matrixUrl;
    private final String matrixRoom;
    private final String matrixToken;

    public MatrixNotification(NotificationConfiguration notificationConfiguration, WebClientSsl ssl) {
        this.webClient = WebClient.builder().apply(ssl.fromBundle(notificationConfiguration.getSsl().getBundle())).build();
        this.matrixUrl = notificationConfiguration.getMatrix().getUrl();
        this.matrixRoom = notificationConfiguration.getMatrix().getRoom();
        this.matrixToken = notificationConfiguration.getMatrix().getToken();
    }

    @Override
    public void sendNotification(Alert alert) {
        String url = this.matrixUrl + "/_matrix/client/v3/rooms/" + this.matrixRoom + ":cyber.ee/send/m.room.message/" + alert.getTimestamp().toEpochMilli();
        HashMap <String, String> body = new HashMap<String, String>();
        body.put("body", alert.getStatusMessage());
        body.put("msgtype", "m.text");
        webClient.put()
          .uri(url)
          .bodyValue(body)
          .header("Content-Type", "application/json")
          .header("Authorization", this.matrixToken)
          .retrieve()
          .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
        clientResponse -> clientResponse.bodyToMono(String.class).map(msg -> new RuntimeException("Error response: " + msg))
    )
          .bodyToMono(String.class)
          .block();
    }
}
