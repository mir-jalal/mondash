package net.mirjalal.mondash.controller;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mirjalal.mondash.model.dto.AlertSourceCreateDto;
import net.mirjalal.mondash.model.dto.AlertSourceDeleteDto;
import net.mirjalal.mondash.model.dto.AlertSourceGetDto;
import net.mirjalal.mondash.service.AlertSourceService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/alert-source")
public class AlertSourceController {
    private final AlertSourceService alertSourceService;

    @PostMapping
    public AlertSourceCreateDto createAlertSource(@RequestBody AlertSourceCreateDto request) {
        return alertSourceService.create(request);
    }

    @GetMapping
    public List<AlertSourceGetDto> getAllAlertSources() {
        return alertSourceService.getAll();
    }

    @DeleteMapping
    public void deleteAlertSourceByName(@RequestBody AlertSourceDeleteDto request) {
        alertSourceService.delete(request.getName());
    }
}
