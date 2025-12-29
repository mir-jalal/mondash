package net.mirjalal.mondash.service;

import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientSsl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.mirjalal.mondash.alert.AlertClient;
import net.mirjalal.mondash.alert.ElasticAlert;
import net.mirjalal.mondash.configuration.NotificationConfiguration;
import net.mirjalal.mondash.notification.MatrixNotification;
import net.mirjalal.mondash.notification.Notifier;
import net.mirjalal.mondash.repository.AlertRepository;
import net.mirjalal.mondash.repository.ConfigRepository;

@Service
public class AlertService extends AlertClient{
    
    public AlertService(AlertRepository alertRepository, ConfigRepository configRepository, NotificationConfiguration notificationConfiguration, WebClientSsl ssl) {
        this.setAlertStrategy(new ElasticAlert(alertRepository, configRepository));
        Notifier notifier = new Notifier();
        notifier.setNotificationStrategy(new MatrixNotification(notificationConfiguration, ssl));
        this.addListener(notifier);
    }

    @Scheduled(fixedRate = 60000)
    public void runActiveAlerts() {
        getActiveAlerts();
    }
}
