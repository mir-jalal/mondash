package net.mirjalal.mondash.model.dto;

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class AlertSourceGetDto {

    private BigInteger id;
    private String name;
    private List<AlertSourceParameterDto> parameters;
}
