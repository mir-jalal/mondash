package net.mirjalal.mondash.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.mirjalal.mondash.alert.AlertClient;
import net.mirjalal.mondash.alert.AlertClientFactory;
import net.mirjalal.mondash.configuration.NotificationConfiguration;
import net.mirjalal.mondash.model.Alert;
import net.mirjalal.mondash.model.AlertSource;
import net.mirjalal.mondash.model.Config;
import net.mirjalal.mondash.model.factory.AlertSourceFactory;
import net.mirjalal.mondash.notification.MatrixNotification;
import net.mirjalal.mondash.notification.Notifier;
import net.mirjalal.mondash.repository.AlertSourceRepository;
import net.mirjalal.mondash.repository.ConfigRepository;

@Service
public class AlertService {
    private final AlertRepository alertRepository;
    
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public Iterable<Alert> getAlerts() {
        return alertRepository.findAll(Pageable.unpaged());
    }

    public Optional<Alert> getAlert(String id) {
        return alertRepository.findById(id);
    }
}
