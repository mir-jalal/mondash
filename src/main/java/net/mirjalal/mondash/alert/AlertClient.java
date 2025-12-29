package net.mirjalal.mondash.alert;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

import net.mirjalal.mondash.model.Alert;
import lombok.Setter;

@Setter
public class AlertClient {
    
    public static final String NOTIFIERS = "NOTIFIERS";
    private AlertStrategy alertStrategy;
    private final PropertyChangeSupport notifiers = new PropertyChangeSupport(this);

    public void addListener(PropertyChangeListener notifier) {
        notifiers.addPropertyChangeListener(NOTIFIERS, notifier);
    }

    public Iterable<Alert> getActiveAlerts() {
        Iterable<Alert> alerts = this.alertStrategy.getActiveAlerts();
        for(Alert alert : alerts) {
            notifiers.firePropertyChange(NOTIFIERS, null, alert);
        }
        return alerts;
    }
    public Iterable<Alert> getAlerts() {
        return this.alertStrategy.getAlerts();
    }
    public Optional<Alert> getAlert(String id) {
        return this.alertStrategy.getAlert(id);
    }
}
