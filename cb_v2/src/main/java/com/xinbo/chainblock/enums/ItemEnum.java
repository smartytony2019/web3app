package com.xinbo.chainblock.enums;

import cn.hutool.core.util.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @date 8/9/22 10:21 下午
 * @desc file desc
 */
@AllArgsConstructor
public enum ItemEnum {
    HASH_BET(100010, "投注"),
    HASH_BET_SETTLE(100110, "结算");

    int code;
    String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ItemEnum valueOf(int code) {
        for (ItemEnum e : ItemEnum.values()) {
            if (code == e.getCode()) {
                return e;
            }
        }
        return null;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer,String> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(ItemEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), ItemEnum.valueOf((int) code).getMsg());
        }
        return map;
    }
}
