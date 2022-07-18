package com.xinbo.chainblock.consts;

/**
 * @author tony
 * @date 7/1/22 4:35 下午
 * @desc file desc
 */
public interface GlobalConst {

    /**
     * JWT载体key
     */
    String JWT_PAYLOAD_KEY = "payload";
    String JWT_SECRET_KEY = "p2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D*G-KaPdS";
    String TOKEN_PREFIX = "Bearer ";
    String TOKEN_HEADER = "Authorization";
    String TOKEN_TYPE = "JWT";

    String TOKEN_USER_INFO_CLAIM = "user_info";
    String TOKEN_ISSUER = "security";
    String TOKEN_AUDIENCE = "security-all";

    String ADMIN_LOGIN = "/admin/index/login";

    /**
     * 当 Remember 是 false 时，token 有效时间 2 小时(测试阶段时间搞长一点)
     */
    long EXPIRATION_TIME = 60 * 60 * 2L * 100;

    /**
     * 当 Remember 是 true 时，token 有效时间 7 天
     */
    long EXPIRATION_REMEMBER_TIME = 60 * 60 * 24 * 7L;


    String DEFAULT_LANGUAGE = "zh";

    String ROLE_TYPE_USER = "user";
    String ROLE_TYPE_ADMIN = "admin";

}
