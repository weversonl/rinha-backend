package br.widsl.rinhabackend.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.widsl.rinhabackend.annotations.impl.StringArrayValidator;
import br.widsl.rinhabackend.constants.Constants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = StringArrayValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface StringArray {
    String message() default Constants.ARRAY_ITEM_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int maxLength() default Constants.ARRAY_ITEM_SIZE;
}
