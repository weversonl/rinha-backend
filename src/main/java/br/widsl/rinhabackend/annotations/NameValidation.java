package br.widsl.rinhabackend.annotations;

import br.widsl.rinhabackend.annotations.impl.NameValidator;
import br.widsl.rinhabackend.constants.Constants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameValidation {
    String message() default Constants.NAME_ERROR_VALIDATION;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
