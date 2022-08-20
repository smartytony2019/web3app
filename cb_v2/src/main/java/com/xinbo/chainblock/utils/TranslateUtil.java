package com.xinbo.chainblock.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinbo.chainblock.consts.GlobalConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @author tony
 * @date 8/20/22 6:48 下午
 * @desc file desc
 */
@Component
public class TranslateUtil {

    @Autowired
    private HttpServletRequest httpServletRequest;
    private static HttpServletRequest request;


    @PostConstruct
    public void init() {
        request = httpServletRequest;
    }


    public static String translate(String key) {
        String language = GlobalConst.DEFAULT_LANGUAGE;
        try {
            language = StringUtils.isEmpty(request.getHeader("language")) ? GlobalConst.DEFAULT_LANGUAGE : request.getHeader("language");
        } catch (Exception ex) {
        }

        String result = "";
        try {
            String path = String.format("classpath:json/%s.json", language);
            String str = ResourceUtil.readUtf8Str(path);
            JSONObject jsonObject = JSON.parseObject(str);
            Object value = jsonObject.get(key);
            if(!ObjectUtils.isEmpty(value)) {
                result = jsonObject.get(key).toString();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

}
