package net.mirjalal.mondash.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy=SourceParameterValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SourceParameterValid {
    String message() default "{source.parameters.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

