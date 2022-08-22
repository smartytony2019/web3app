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
 * @date 8/17/22 4:37 下午
 * @desc file desc
 */
@AllArgsConstructor
public enum TransferEnum {


    FUNDING2TRADING(0, "0", "转换(资金帐户=>交易帐号)"),
    TRADING2FUNDING(1, "1", "转换(交易帐号=>资金帐户)");

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
        for (TransferEnum  e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(String.valueOf(code)).nameZh(TranslateUtil.translate(e.getName())).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static Map<Integer, EnumItemBo> toMap() {
        Map<Integer, EnumItemBo> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(TransferEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), TransferEnum.valueOf((int) code));
        }
        return map;
    }

}
