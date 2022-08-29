package com.xinbo.chainblock.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
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

    private static final byte[] secretKey = DatatypeConverter.parseBase64Binary(GlobalConst.JWT_SECRET_KEY);


    private JwtUtil() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }

    /**
     * 生成token
     *
     * @param jwtUserBo
     * @return
     */
    public static String generateToken(JwtUserBo jwtUserBo) {
        byte[] jwtSecretKey = DatatypeConverter.parseBase64Binary(GlobalConst.JWT_SECRET_KEY);
        // 生成 token
        return Jwts.builder()
                // 生成签证信息
                .setHeaderParam("typ", GlobalConst.TOKEN_TYPE)
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey), SignatureAlgorithm.HS256)
                .claim(GlobalConst.TOKEN_USER_INFO_CLAIM, JSON.toJSONString(jwtUserBo))
                .setIssuer(GlobalConst.TOKEN_ISSUER)
                .setIssuedAt(new Date())
                .setAudience(GlobalConst.TOKEN_AUDIENCE)
                // 设置有效时间
                .setExpiration(new Date(System.currentTimeMillis() + GlobalConst.EXPIRATION_TIME * 1000))
                .compact();
    }


    public static JwtUserBo parseToken(String token) {
        Claims claims = getTokenBody(token);
        if (claims == null) {
            return null;
        }
        Optional<Claims> optional = Optional.of(claims);
        String json = optional.get().get(GlobalConst.TOKEN_USER_INFO_CLAIM, String.class);
        return JSON.parseObject(json, JwtUserBo.class);

//        HashMap<String, Object> map = optional.get().get(GlobalConst.TOKEN_USER_INFO_CLAIM);
//        int id = StringUtils.isEmpty(map.get("id")) ? 0 : Integer.parseInt(map.get("id").toString());
//        String username = StringUtils.isEmpty(map.get("username")) ? "" : map.get("username").toString();
//        List<String> authority = StringUtils.isEmpty(map.get("authority")) ? new ArrayList<>() : (List<String>) map.get("authority");
//        return JwtUserBo.builder()
//                .uid(id)
//                .username(username)
//                .build();
    }


    /**
     * 将 Claims 转为 JwtUser
     *
     * @return
     */
    public static JwtUserBo getJwtUser() {
        String authHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(GlobalConst.TOKEN_HEADER);
        final String token = authHeader.substring(7);
        return JwtUtil.parseToken(token.replace(GlobalConst.TOKEN_PREFIX, ""));
    }


    /**
     * 验证 token 是否有效
     *
     * <p>
     * 如果解析失败，说明 token 是无效的
     *
     * @param token token 信息
     * @return 如果返回 true，说明 token 有效
     */
    public static boolean validateToken(String token) {
        try {
            getTokenBody(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Request to parse expired JWT : {} failed : {}", token, e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", token, e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Request to parse invalid JWT : {} failed : {}", token, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", token, e.getMessage());
        }
        return false;
    }


    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

}

