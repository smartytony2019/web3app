package com.xinbo.chainblock.enums;

import cn.hutool.core.util.EnumUtil;
import com.xinbo.chainblock.bo.EnumItemBo;
import com.xinbo.chainblock.utils.TranslateUtil;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @date 8/9/22 10:21 下午
 * @desc file desc
 */
@AllArgsConstructor
public enum MemberFlowItemEnum {
    HASH_BET(500010, "500010", "投注"),
    HASH_BET_SETTLE(500020,"500020", "结算"),

    RECHARGE(500100,"500100", "充值"),
    WITHDRAW(500110,"500110", "提现"),

    TRANSFER_FUNDING2TRADING(500210,"500210", "转换(资金帐户=>交易帐号)"),
    TRANSFER_TRADING2FUNDING(500220,"500220", "转换(交易帐号=>资金帐户)"),


    ACTIVITY_RECEIVE(500310,"500310", "活动领取"),
    AGENT_COMMISSION(500410,"500410", "代理佣金")
    ;

    int code;
    String name;
    String nameZh;

    public int getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public String getNameZh() {
        return nameZh;
    }

    public static EnumItemBo valueOf(int code) {
        for (MemberFlowItemEnum  e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(String.valueOf(code)).nameZh(TranslateUtil.translate(e.getName())).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static Map<Integer, EnumItemBo> toMap() {
        Map<Integer, EnumItemBo> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(MemberFlowItemEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), MemberFlowItemEnum.valueOf((int) code));
        }
        return map;
    }
}
