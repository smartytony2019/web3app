package com.xinbo.chainblock.utils;


import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.bo.DateRangeBo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class CommonUtils {

    public static double toTrx(float value) {
        BigDecimal b1 = new BigDecimal(value);
        BigDecimal b2 = new BigDecimal(String.valueOf(Math.pow(10, 6)));
        BigDecimal b3 = b1.multiply(b2);
        return b3.floatValue();
    }

    public static float fromTrx(float value) {
        BigDecimal b1 = new BigDecimal(value);
        BigDecimal b2 = new BigDecimal(String.valueOf(Math.pow(10, 6)));
        BigDecimal b3 = b1.divide(b2, 2, RoundingMode.DOWN);
        return b3.floatValue();
    }


    public static BigDecimal toTrc20(String value) {
        BigDecimal b1 = new BigDecimal(value);
        BigDecimal b2 = new BigDecimal(String.valueOf(Math.pow(10, 18)));
        return b1.multiply(b2);
    }

    public static BigDecimal fromTrc20(BigDecimal value) {
        BigDecimal bigInteger = new BigDecimal(String.valueOf(Math.pow(10, 18)));
        return value.divide(bigInteger, 6, RoundingMode.CEILING);
    }

    public static DateRangeBo toConvertDate(int type) {
        if (type == 0) {
            return DateRangeBo.builder().build();
        }

        Date startTime = DateUtil.parse(DateUtil.today());
        Date endTime = DateUtil.parse(DateUtil.today());
        if (type == 1) {
            startTime = DateUtil.parse(DateUtil.today());
            endTime = DateUtil.parse(DateUtil.today());
        }
        if (type == 2) {
            startTime = DateUtil.parse(DateUtil.yesterday().toDateStr());
            endTime = DateUtil.parse(DateUtil.yesterday().toDateStr());
        }
        if (type == 3) {
            startTime = DateUtil.parse(DateUtil.lastMonth().toDateStr());
            endTime = DateUtil.parse(DateUtil.today());
        }

        return DateRangeBo.builder()
                .startTime(DateUtil.beginOfDay(startTime))
                .endTime(DateUtil.endOfDay(endTime))
                .build();
    }

}
