package com.xinbo.chainblock.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@Service
public class CommonUtils {

    public double toTrx(float value) {
        BigDecimal b1 = new BigDecimal(value);
        BigDecimal b2 = new BigDecimal(String.valueOf(Math.pow(10, 6)));
        BigDecimal b3 = b1.multiply(b2);
        return b3.floatValue();
    }

    public float fromTrx(float value) {
        BigDecimal b1 = new BigDecimal(value);
        BigDecimal b2 = new BigDecimal(String.valueOf(Math.pow(10, 6)));
        BigDecimal b3 = b1.divide(b2, 2, RoundingMode.DOWN);
        return b3.floatValue();
    }


    public BigDecimal toTrc20(String value) {
        BigDecimal b1 = new BigDecimal(value);
        BigDecimal b2 = new BigDecimal(String.valueOf(Math.pow(10, 18)));
        return b1.multiply(b2);
    }

    public BigDecimal fromTrc20(BigDecimal value) {
        BigDecimal bigInteger = new BigDecimal(String.valueOf(Math.pow(10, 18)));
        return value.divide(bigInteger,6, RoundingMode.CEILING);
    }

}
