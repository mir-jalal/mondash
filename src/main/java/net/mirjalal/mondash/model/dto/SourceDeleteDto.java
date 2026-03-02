package net.mirjalal.mondash.model.dto;

import jakarta.validation.constraints.NotBlank;
public record SourceDeleteDto (
    @NotBlank
    String name
){}
