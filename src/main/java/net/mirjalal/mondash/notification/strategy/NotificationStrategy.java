package net.mirjalal.mondash.notification.strategy;

import net.mirjalal.mondash.model.Alert;

public interface NotificationStrategy {
    public void sendNotification(Alert alert);
}
