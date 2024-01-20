package br.widsl.rinhabackend.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.widsl.rinhabackend.annotations.impl.DateValidator;
import br.widsl.rinhabackend.constants.Constants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDate {
    String message() default Constants.INVALID_DATE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
