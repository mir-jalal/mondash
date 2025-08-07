package net.mirjalal.mondash.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.mirjalal.mondash.model.Alert;
import net.mirjalal.mondash.service.AlertService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/alerts")
public class AlertViewController {
    private final AlertService alertService;

    public AlertViewController(AlertService alertService) {
        this.alertService = alertService;
    }
    
    @GetMapping
    public ModelAndView getAlerts(Map<String, Object> model) {
        List<Alert> alerts = new ArrayList<>();
        alertService.getAlerts().forEach(alerts::add);
        model.put("alerts", alerts);
        return new ModelAndView("alerts", model);
    }
    
}
