package com.beusable.roos;

import java.math.BigDecimal;
import java.util.List;

public class TestBase {
    protected static BigDecimal b(double value) {
        return new BigDecimal(value);
    }

    protected static BigDecimal b(String value) {
        return new BigDecimal(value);
    }

    protected static final List<BigDecimal> POTENTIAL_GUESTS =
            List.of(b(23), b(45), b(155), b(374), b(22), b("99.99"), b(100), b(101), b(115), b(209));
}
