package com.beusable.roos.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultConstants {
    public static final BigDecimal PREMIUM_PRICE_THRESHOLD = new BigDecimal(100);
}