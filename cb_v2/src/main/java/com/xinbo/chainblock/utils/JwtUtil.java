package com.xinbo.chainblock.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.consts.GlobalConst;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author tony
 * @date 2021/2/5 15:52
 * @desc file desc
 */
@Slf4j
public class JwtUtil {


    public static String createToken(JwtUserBo bo) {
        JWTSigner jwtSigner = JWTSignerUtil.hs256(GlobalConst.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        String sign = JWT.create().setSigner(jwtSigner)
                .setIssuedAt(DateUtil.date())
                .setNotBefore(DateUtil.date())
                .setExpiresAt(DateUtil.nextWeek())
                .setPayload(GlobalConst.JWT_PAYLOAD_KEY, JSON.toJSONString(bo))
                .sign();
        return sign;
    }

    public static JwtUserBo parseToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        Object payload = jwt.getPayload(GlobalConst.JWT_PAYLOAD_KEY);
        return JSON.parseObject(String.valueOf(payload), JwtUserBo.class);
    }

    public static boolean verify(String token) {
        return JWTUtil.verify(token, GlobalConst.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取用户
     * @return
     */
    public static JwtUserBo getJwtUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(ObjectUtils.isEmpty(attributes)) {
           throw new RuntimeException();
        }
        String authHeader = attributes.getRequest().getHeader(GlobalConst.TOKEN_HEADER);
        final String token = authHeader.substring(7).replace(GlobalConst.TOKEN_PREFIX, "");
        return JwtUtil.parseToken(token);
    }

}
