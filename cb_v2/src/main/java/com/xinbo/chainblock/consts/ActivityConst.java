package com.xinbo.chainblock.consts;

/**
 * @author tony
 * @date 8/25/22 4:09 下午
 * @desc file desc
 */
public interface ActivityConst {

    int ACTIVITY_TYPE_FIRST_RECHARGE = 1;
    int ACTIVITY_TYPE_REGISTER = 2;
    int ACTIVITY_TYPE_SIGN = 3;
    int ACTIVITY_FIRST_OTHER = 10;


    int RECORD_UNHANDLED = 0;       //未处理
    int RECORD_COMPLETE = 1;        //完成
    int RECORD_REJECT = 2;          //拒绝


    int RULE_JUST_SEND = 1;         // 直接发放
    int RULE_ADMIN_CHECK = 2;       // 后端审核
    int RULE_AUTO_SEND = 3;              // 自动发放
}
