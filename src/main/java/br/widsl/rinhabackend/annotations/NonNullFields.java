package br.widsl.rinhabackend.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.widsl.rinhabackend.annotations.impl.NonNullFieldsValidator;
import br.widsl.rinhabackend.constants.Constants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = NonNullFieldsValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NonNullFields {
    String message() default Constants.FIELD_REQUIRED;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName();

}
