package net.mirjalal.mondash.model.dto;

import jakarta.validation.constraints.NotBlank;

public record SourceParameterDto(
    @NotBlank
    String key,
    @NotBlank
    String value,
    Boolean sensitive
) {}
