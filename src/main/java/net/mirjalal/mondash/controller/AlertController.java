package net.mirjalal.mondash.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mirjalal.mondash.model.Alert;
import net.mirjalal.mondash.service.AlertService;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    private final AlertService alertService;
    
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping()
    public Iterable<Alert> getAlerts() {
        return alertService.getAlerts();
    }    
}
