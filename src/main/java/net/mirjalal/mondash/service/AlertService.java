package net.mirjalal.mondash.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

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
public class AlertService {

    private final AlertClient alertClient;
    private final ConfigRepository configRepository;

    public AlertService(AlertSourceFactory alertSourceFactory, ConfigRepository configRepository, AlertSourceRepository alertSourceRepository, NotificationConfiguration notificationConfiguration, WebClientSsl ssl) {
        this.configRepository = configRepository;
        AlertSource alertSource = (AlertSource) sourceFactory.decryptSource(alertSourceRepository.getByName("elastic"));
        this.alertClient = AlertClientFactory.createAlertClient(alertSource);
        Notifier notifier = new Notifier();
        notifier.setNotificationStrategy(new MatrixNotification(notificationConfiguration, ssl));
        this.alertClient.addListener(notifier);
    }

    @Scheduled(fixedRate = 15000)
    public void runActiveAlerts() {
        Optional<Config> optionalLatestTimestamp = configRepository.findByKey("alertLatestTimestamp");

        Config latestTimestamp;
        LocalDateTime nowTimestamp = LocalDateTime.now(ZoneId.of("UTC"));

        if(optionalLatestTimestamp.isPresent()) {
            latestTimestamp = optionalLatestTimestamp.get();
            if(latestTimestamp.getValue().equals("")){
                latestTimestamp.setValue(nowTimestamp.minusMonths(1).toString());
            }
        }
        else {
            latestTimestamp = new Config("alertLatestTimestamp", nowTimestamp.minusMonths(1).toString());      
        }

        this.alertClient.getActiveAlerts(latestTimestamp.getValue());

        latestTimestamp.setValue(nowTimestamp.toString());

        configRepository.save(latestTimestamp);
    }

    public Iterable<Alert> getAlerts() {
        return this.alertClient.getAlerts();
    }
}
