package net.mirjalal.mondash.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.mirjalal.mondash.alert.AlertClient;
import net.mirjalal.mondash.alert.AlertClientFactory;
import net.mirjalal.mondash.model.Alert;
import net.mirjalal.mondash.model.AlertSource;
import net.mirjalal.mondash.model.Config;
import net.mirjalal.mondash.model.NotifierSource;
import net.mirjalal.mondash.model.factory.SourceFactory;
import net.mirjalal.mondash.notification.Notifier;
import net.mirjalal.mondash.notification.strategy.MatrixNotification;
import net.mirjalal.mondash.repository.AlertSourceRepository;
import net.mirjalal.mondash.repository.ConfigRepository;
import net.mirjalal.mondash.repository.NotifierSourceRepository;

@Service
public class AlertService {

    private final AlertClient alertClient;
    private final ConfigRepository configRepository;

    public AlertService(SourceFactory sourceFactory, ConfigRepository configRepository, AlertSourceRepository alertSourceRepository, NotifierSourceRepository notifierSourceRepository) throws KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
        this.configRepository = configRepository;
        AlertSource alertSource = (AlertSource) sourceFactory.decryptSource(alertSourceRepository.getByName("elastic"));
        this.alertClient = AlertClientFactory.createAlertClient(alertSource);
        Notifier notifier = new Notifier();
        NotifierSource notifierSource = (NotifierSource) sourceFactory.decryptSource(notifierSourceRepository.getByName("id-matrix"));
        notifier.setNotificationStrategy(new MatrixNotification(notifierSource));
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
