package com.xinbo.chainblock.utils;

import com.google.common.collect.Maps;
import com.xinbo.chainblock.consts.GlobalConst;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.util.*;

/**
 * @author 熊二
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
     * @param jwtUser
     * @return
     */
    public static String generateToken(JwtUser jwtUser) {
        byte[] jwtSecretKey = DatatypeConverter.parseBase64Binary(GlobalConst.JWT_SECRET_KEY);

        Map<String,Object> claim =  Maps.newHashMap();
        claim.put("id", jwtUser.getUid());
        claim.put("username", jwtUser.getUsername());
        claim.put("authority", jwtUser.getAuthority());

        // 生成 token
        String token = Jwts.builder()
                // 生成签证信息
                .setHeaderParam("typ", GlobalConst.TOKEN_TYPE)
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey), SignatureAlgorithm.HS256)
                .claim(GlobalConst.TOKEN_USER_INFO_CLAIM, claim)
                .setIssuer(GlobalConst.TOKEN_ISSUER)
                .setIssuedAt(new Date())
                .setAudience(GlobalConst.TOKEN_AUDIENCE)
                // 设置有效时间
                .setExpiration(new Date(System.currentTimeMillis() + GlobalConst.EXPIRATION_TIME * 1000))
                .compact();
        return token;
    }
    public static JwtUser parseToken(String token) {
        Claims claims = getTokenBody(token);
        if (claims == null) {
            return null;
        }
        Optional<Claims> optional = Optional.of(claims);
        if (!optional.isPresent()) {
            return null;
        }

        HashMap<String, Object> map = (HashMap<String, Object>) optional.get().get(GlobalConst.TOKEN_USER_INFO_CLAIM);

        int id = StringUtils.isEmpty(map.get("id")) ? 0 : Integer.parseInt(map.get("id").toString());
        String username = StringUtils.isEmpty(map.get("username")) ? "" : map.get("username").toString();
        List<String> authority = StringUtils.isEmpty(map.get("authority")) ? new ArrayList<>() : (List<String>) map.get("authority");
        return JwtUser.builder()
                .uid(id)
                .username(username)
                .authority(authority)
                .build();
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
