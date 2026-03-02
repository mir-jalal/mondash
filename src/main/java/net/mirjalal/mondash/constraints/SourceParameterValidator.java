package net.mirjalal.mondash.constraints;

import java.util.List;
import java.util.Map;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.mirjalal.mondash.model.dto.SourceCreateDto;

public class SourceParameterValidator
        implements ConstraintValidator<SourceParameterValid, SourceCreateDto> {

    private static final String TYPE_PARAM = "type";

    private static final Map<String, List<String>> REQUIRED_PARAMS_BY_TYPE = Map.of(
            "elastic", List.of(
                "username",
                "password",
                "caCert",
                "indexName",
                "url"
            ),
            "matrix", List.of(
                "uri",
                "room",
                "token",
                "caCert"
            )
    );

    @Override
    public boolean isValid(SourceCreateDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true; // @NotNull should handle this if needed
        }

        context.disableDefaultConstraintViolation();

        String type = dto.getParameter(TYPE_PARAM);
        if (isMissing(type)) {
            return addViolation(context, TYPE_PARAM, "{source.type.required}");
        }

        List<String> requiredParams = REQUIRED_PARAMS_BY_TYPE.get(type.toLowerCase());
        if (requiredParams == null) {
            return true; // Unknown types are allowed by design
        }

        for (String param : requiredParams) {
            if (isMissing(dto.getParameter(param))) {
                return addViolation(
                        context,
                        param,
                        "{source.parameter.required}"
                );
            }
        }

        return true;
    }

    private boolean isMissing(String value) {
        return value == null || value.isBlank();
    }

    private boolean addViolation(
            ConstraintValidatorContext context,
            String property,
            String messageKey
    ) {
        context.buildConstraintViolationWithTemplate(messageKey)
               .addPropertyNode(property)
               .addConstraintViolation();
        return false;
    }
}
