package net.mirjalal.mondash.alert;

import net.mirjalal.mondash.model.Alert;

public interface AlertStrategy {
    public Iterable<Alert> getActiveAlerts(String timestamp);
    public Iterable<Alert> getAlerts();
}
