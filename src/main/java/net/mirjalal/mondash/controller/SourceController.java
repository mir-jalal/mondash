package net.mirjalal.mondash.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import net.mirjalal.mondash.model.dto.SourceCreateDto;
import net.mirjalal.mondash.model.dto.SourceDeleteDto;
import net.mirjalal.mondash.model.dto.SourceGetDto;
import net.mirjalal.mondash.model.factory.SourceFactory;
import net.mirjalal.mondash.service.SourceService;

public abstract class SourceController {
    public abstract SourceFactory.SourceType getSourceType();
    public abstract SourceService getSourceService();

    @PostMapping
    public ResponseEntity<BigInteger> createAlertSource(@RequestBody @Valid SourceCreateDto request) {
        return ResponseEntity.status(201).body(BigInteger.ONE);             //getSourceService().create(request, this.getSourceType()));
    }

    @GetMapping
    public List<SourceGetDto> getAllAlertSources() {
        return getSourceService().getAll();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAlertSourceByName(@RequestBody @Valid SourceDeleteDto request) {
        getSourceService().delete(request.name());
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/{id}")
    public SourceGetDto getAlertSource(@PathVariable @NotNull @DecimalMin("0") Long id) {
        return getSourceService().get(id);
    }
}
