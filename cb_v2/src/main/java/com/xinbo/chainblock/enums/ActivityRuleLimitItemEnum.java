package com.xinbo.chainblock.enums;

import cn.hutool.core.util.EnumUtil;
import com.xinbo.chainblock.bo.EnumItemBo;
import com.xinbo.chainblock.utils.TranslateUtil;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @date 8/9/22 10:21 下午
 * @desc 活动规则限制项
 */
@AllArgsConstructor
public enum ActivityRuleLimitItemEnum {
    RECHARGE(1,"600060", "充值"),
    BET(2,"600061", "打码"),
    SIGN(3,"600062", "签到"),
    BET_COUNT(4,"600063", "打码次数")
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
        for (ActivityRuleLimitItemEnum e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(TranslateUtil.translate(e.getName())).nameZh(e.getNameZh()).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static Map<Integer, EnumItemBo> toMap() {
        Map<Integer, EnumItemBo> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(ActivityRuleLimitItemEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), ActivityRuleLimitItemEnum.valueOf((int) code));
        }
        return map;
    }

    public static List<EnumItemBo> toList() {
        List<EnumItemBo> list = new ArrayList<>();
        List<Object> codes = EnumUtil.getFieldValues(ActivityRuleLimitItemEnum.class, "code");
        for (Object code : codes) {
            list.add(ActivityRuleLimitItemEnum.valueOf((int) code));
        }
        return list;
    }
}
