package com.xinbo.chainblock.utils;

import org.springframework.stereotype.Service;

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

    public double fromTrc20(double value) {
        return value / Math.pow(10, 18);
    }
}
