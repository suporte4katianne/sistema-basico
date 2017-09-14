package br.com.hsi.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CodigoBarrasValidator.class)

public @interface CodigoBarras {

    String message() default "não é valido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
