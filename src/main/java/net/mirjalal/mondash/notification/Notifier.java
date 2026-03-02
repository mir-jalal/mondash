package net.mirjalal.mondash.notification;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.mirjalal.mondash.model.Alert;
import net.mirjalal.mondash.notification.strategy.NotificationStrategy;
import lombok.Setter;

@Setter
public class Notifier implements PropertyChangeListener {

    private NotificationStrategy notificationStrategy;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Alert alert = (Alert) evt.getNewValue();
        notificationStrategy.sendNotification(alert);
    }
}
