package net.mirjalal.mondash.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.mirjalal.mondash.model.AlertSource;
import net.mirjalal.mondash.model.dto.AlertSourceCreateDto;
import net.mirjalal.mondash.model.dto.AlertSourceGetDto;
import net.mirjalal.mondash.model.factory.AlertSourceFactory;
import net.mirjalal.mondash.repository.AlertSourceRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AlertSourceService {

    private final AlertSourceRepository alertSourceRepository;
    private final AlertSourceFactory alertSourceFactory;

    public AlertSourceCreateDto create(AlertSourceCreateDto request) {
        AlertSource alertSource = alertSourceFactory.createAlertSource(request);

        alertSourceRepository.save(alertSource);
        return request;
    }

    public List<AlertSourceGetDto> getAll() {
        List<AlertSourceGetDto> alertSources = new ArrayList<>();
        alertSourceRepository.findAll().stream()
          .forEach(alertSource -> {
            alertSources.add(alertSourceFactory.createAlertSourceGet(alertSource));
          });

        return alertSources;
    }

    public void delete(String name) {
        alertSourceRepository.deleteByName(name);
    }
}
