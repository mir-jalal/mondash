package net.mirjalal.mondash.alert;

import net.mirjalal.mondash.repository.AlertRepository;
import net.mirjalal.mondash.repository.ConfigRepository;

public final class AlertFactory {
    public static AlertClient createAlertClient(AlertRepository alertRepository, ConfigRepository configRepository) {
        AlertClient alertClient = new AlertClient();

        AlertStrategy alertStrategy = new ElasticAlert(alertRepository, configRepository);
        alertClient.setAlertStrategy(alertStrategy);
        
        return alertClient;
    }
}
