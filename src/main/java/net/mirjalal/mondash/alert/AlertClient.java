package net.mirjalal.mondash.alert;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import net.mirjalal.mondash.alert.strategy.AlertStrategy;
import net.mirjalal.mondash.model.Alert;
import lombok.Setter;

@Setter
public class AlertClient implements PropertyChangeListener {
    
    public static final String NOTIFIERS = "NOTIFIERS";
    private AlertStrategy alertStrategy;
    private final PropertyChangeSupport notifiers = new PropertyChangeSupport(this);

    public void addListener(PropertyChangeListener notifier) {
        notifiers.addPropertyChangeListener(NOTIFIERS, notifier);
    }

    public Iterable<Alert> getActiveAlerts(String timestamp) {
        Iterable<Alert> alerts = this.alertStrategy.getActiveAlerts(timestamp);
        for(Alert alert : alerts) {
            notifiers.firePropertyChange(NOTIFIERS, null, alert);
        }
        return alerts;
    }
    public Iterable<Alert> getAlerts() {
        return this.alertStrategy.getAlerts();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String timestamp = (String) evt.getNewValue();
        this.getActiveAlerts(timestamp);
    }
}
