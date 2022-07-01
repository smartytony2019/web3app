package com.xinbo.chainblock.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    public static String translate(String language, String key) {
        String result = "";
        try {
            String path = String.format("classpath:json/%s.json", language);
            String str = ResourceUtil.readUtf8Str(path);
            JSONObject jsonObject = JSON.parseObject(str);
            result = jsonObject.get(key).toString();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

}
