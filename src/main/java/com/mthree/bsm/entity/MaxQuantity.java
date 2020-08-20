package com.mthree.bsm.entity;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates that a {@link Trade}'s quantity is at most the quantity of its buy order and sell order.
 */
@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { MaxQuantityValidator.class })
@Documented
public @interface MaxQuantity {

    String message() default "{com.mthree.bsm.entity.MaxQuantity.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
