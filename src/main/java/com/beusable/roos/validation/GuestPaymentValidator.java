package com.beusable.roos.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.List;

public class GuestPaymentValidator implements ConstraintValidator<ValidGuestPayments, List<BigDecimal>> {

    public boolean isValid(List<BigDecimal> guestPayments, ConstraintValidatorContext context) {
        if (guestPayments == null) {
            return true;
        } else {
            return guestPayments.stream().allMatch(payment -> payment.compareTo(BigDecimal.ZERO) > 0);
        }
    }

    @Override
    public void initialize(ValidGuestPayments constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
