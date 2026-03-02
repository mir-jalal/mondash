package net.mirjalal.mondash.model.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import net.mirjalal.mondash.constraints.SourceParameterValid;

@SourceParameterValid
public record SourceCreateDto(
    @NotBlank
    String name,

    @NotEmpty
    List<SourceParameterDto> parameters

) {
    public String getParameter(String key) {
        for (SourceParameterDto parameter: parameters) {
            if (parameter.key().equals(key))
                return parameter.value();
        }
        return null;
    }
}
