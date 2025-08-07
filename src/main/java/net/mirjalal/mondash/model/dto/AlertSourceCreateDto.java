package net.mirjalal.mondash.model.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertSourceCreateDto {

    private String name;
    private Set<AlertSourceParameterDto> parameters;
}

