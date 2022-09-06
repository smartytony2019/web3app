package com.xinbo.chainblock.consts;

/**
 * @author tony
 * @date 6/28/22 7:40 下午
 * @desc file desc
 */
public interface RedisConst {


    String HASH_BET = "cb:hash:bet";

    String HASH_RESULT = "trx:hash:result:%s:%s";

    String MEMBER_REGISTER = "cb:member:register";

    String MEMBER_TRANSFER = "cb:member:transfer";

    String MEMBER_BALANCE = "cb:member:balance:%s";

    String MEMBER_FINANCE = "cb:member:finance";

    String MEMBER_TOKEN = "cb:member:token:%s";


    String SYSTEM_FLOW = "cb:system:flow";
    String SYSTEM_CONFIG = "cb:system:config";
}
