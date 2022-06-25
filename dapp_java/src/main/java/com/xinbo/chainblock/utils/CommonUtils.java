package com.xinbo.chainblock.utils;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@Service
public class CommonUtils {

    public double toTrx(double value) {
        return value * Math.pow(10, 6);
    }

    public double fromTrx(double value) {
        return value / Math.pow(10, 6);
    }


    public double toTrc20(double value) {
        return value * Math.pow(10, 18);
    }

    public BigDecimal fromTrc20(BigDecimal value) {
        BigDecimal bigInteger = new BigDecimal(String.valueOf(Math.pow(10, 18)));
        return value.divide(bigInteger,6, RoundingMode.CEILING);
    }
}
