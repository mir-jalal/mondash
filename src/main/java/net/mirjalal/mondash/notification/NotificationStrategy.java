package net.mirjalal.mondash.notification;

import net.mirjalal.mondash.model.Alert;

public interface NotificationStrategy {
    public void sendNotification(Alert alert);
}
