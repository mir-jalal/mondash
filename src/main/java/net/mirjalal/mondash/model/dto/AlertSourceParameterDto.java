package net.mirjalal.mondash.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AlertSourceParameterDto {
    private String key;
    private String value;
    private Boolean sensitive;
}
