package br.widsl.rinhabackend.annotations;

import br.widsl.rinhabackend.annotations.impl.StringArrayValidator;
import br.widsl.rinhabackend.constants.Constants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringArrayValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringArray {
    String message() default Constants.ARRAY_ITEM_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int maxLength() default Constants.ARRAY_ITEM_SIZE;
}
