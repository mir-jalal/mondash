package net.mirjalal.mondash.alert;

import java.util.Optional;

import net.mirjalal.mondash.model.Alert;

public interface AlertStrategy {
    public Iterable<Alert> getActiveAlerts();
    public Iterable<Alert> getAlerts();
    public Optional<Alert> getAlert(String id);
}
