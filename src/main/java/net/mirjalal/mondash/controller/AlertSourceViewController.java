package net.mirjalal.mondash.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import net.mirjalal.mondash.model.dto.AlertSourceGetDto;
import net.mirjalal.mondash.service.AlertSourceService;
import lombok.AllArgsConstructor;


@Controller
@RequestMapping("/alert-sources")
@AllArgsConstructor
public class AlertSourceViewController {

    private final AlertSourceService alertSourceService;
    
    @GetMapping()
    public ModelAndView getAlertSources(Map<String, Object> model) {
        List<AlertSourceGetDto> alertSources = alertSourceService.getAll();
        model.put("alert-sources", alertSources);
        return new ModelAndView("alert-sources", model);
    }
}
