package com.beusable.roos.config;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultConstants {
    public static final BigDecimal PREMIUM_PRICE_THRESHOLD = new BigDecimal(100);
}
