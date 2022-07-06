package com.xinbo.chainblock.consts;

/**
 * @author tony
 * @date 6/28/22 7:40 下午
 * @desc file desc
 */
public interface RedisConst {


    String ADMIN_JWT_KEY = "cloud:jwt:admin:{0}"; //0:uid

    String HASH_RESULT = "trx:hash:result:%s:%s";

    String AGENT_FIXED = "agent:fixed";

}
