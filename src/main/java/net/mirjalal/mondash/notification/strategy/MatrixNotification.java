package net.mirjalal.mondash.notification.strategy;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;

import org.springframework.web.reactive.function.client.WebClient;
import net.mirjalal.mondash.http.HttpUtils;
import net.mirjalal.mondash.model.Alert;
import net.mirjalal.mondash.model.NotifierSource;

public class MatrixNotification implements NotificationStrategy {

    private final WebClient webClient;
    private final String matrixUrl;
    private final String matrixRoom;
    private final String matrixToken;

    public MatrixNotification(NotifierSource notifierSource) throws NoSuchAlgorithmException, KeyManagementException, CertificateException, KeyStoreException, IOException {
        String caCerString = notifierSource.getParameterValue("caCert");
        this.webClient = HttpUtils.createWebClient(caCerString);
        this.matrixUrl = notifierSource.getParameterValue("uri");
        this.matrixRoom = notifierSource.getParameterValue("room");
        this.matrixToken = notifierSource.getParameterValue("token");
    }

    @Override
    public void sendNotification(Alert alert) {
        String url = this.matrixUrl + "/_matrix/client/v3/rooms/" + this.matrixRoom + "/send/m.room.message/" + alert.getTimestamp().toEpochMilli() + "1";
        HashMap <String, String> body = new HashMap<>();
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
