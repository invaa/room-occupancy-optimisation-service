package com.beusable.roos.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GuestPaymentValidator.class)
@Documented
public @interface ValidGuestPayments {

    String message() default "Each guest payment must be greater than zero";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
