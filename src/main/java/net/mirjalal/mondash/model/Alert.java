package net.mirjalal.mondash.model;

import com.fasterxml.jackson.databind.node.ObjectNode;

import co.elastic.clients.util.DateTime;
import lombok.Data;

@Data
public class Alert {
    private String id;
    private String latestErrorMessage;
    private String monitorName;
    private String monitorUrl;
    private String statusMessage;
    private DateTime timestamp;

    public static Alert createAlert(ObjectNode document) {
        Alert alert = new Alert();
        alert.setLatestErrorMessage(document.get("latestErrorMessage").asText("null"));
        alert.setMonitorName(document.get("monitorName").asText("null"));
        alert.setMonitorUrl(document.get("monitorUrl").asText("null"));
        alert.setStatusMessage(document.get("statusMessage").asText("null"));
        alert.setTimestamp(DateTime.of((document.get("@timestamp").asText())));
        return alert;
    }
}
