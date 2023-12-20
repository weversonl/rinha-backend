package br.widsl.rinhabackend.annotations;

import br.widsl.rinhabackend.annotations.impl.DateValidator;
import br.widsl.rinhabackend.constants.Constants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDate {
    String message() default Constants.INVALID_DATE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
