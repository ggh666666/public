package com.fh.shop.api.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

    public static BigDecimal add(String a, String b) {
        return new BigDecimal(a).add(new BigDecimal(b));
    }

    public static BigDecimal multiply(String a, String b) {
        return new BigDecimal(a).multiply(new BigDecimal(b));
    }
}
