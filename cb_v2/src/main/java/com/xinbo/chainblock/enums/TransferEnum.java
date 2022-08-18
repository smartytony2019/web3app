package com.xinbo.chainblock.enums;

import cn.hutool.core.util.EnumUtil;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @date 8/17/22 4:37 下午
 * @desc file desc
 */
@AllArgsConstructor
public enum TransferEnum {


    FUNDING2TRADING(0, "转换(资金帐户=>交易帐号)"),
    TRADING2FUNDING(1, "转换(交易帐号=>资金帐户)");
    int code;
    String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static TransferEnum valueOf(int code) {
        for (TransferEnum e : TransferEnum.values()) {
            if (code == e.getCode()) {
                return e;
            }
        }
        return null;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer,String> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(TransferEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), TransferEnum.valueOf((int) code).getMsg());
        }
        return map;
    }

}
