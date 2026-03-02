package net.mirjalal.mondash.alert;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class AlertManager {
    public static final String ALERT_CLIENTS = "ALERT_CLIENTS";
    private final PropertyChangeSupport alertClients = new PropertyChangeSupport(this);
    private final Map<String, AlertClient> alertClientMap = new HashMap<>();

    private void addAlertClientListener(PropertyChangeListener alertClient) {
        alertClients.addPropertyChangeListener(alertClient);
    }

    private void removeAlertClientListener(PropertyChangeListener alertClient) {
        alertClients.removePropertyChangeListener(alertClient);
    }

    public void addAlertClient(String key, AlertClient alertClient) {
        alertClientMap.put(key, alertClient);
        this.addAlertClientListener(alertClient);
    }

    public void removeAlertClient(String key) {
        this.removeAlertClientListener( alertClientMap.get(key) );
    }

    public void fireAlerts(String timestamp) {
        alertClients.firePropertyChange(ALERT_CLIENTS, null, timestamp);
    }
}
