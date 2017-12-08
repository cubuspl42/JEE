package pl.gda.pg.eti.kask.javaee.enterprise.entities.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameValidator.class)
public @interface Name {

    String message() default "{Imiona muszą być wielką literą}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String name() default "";
}
