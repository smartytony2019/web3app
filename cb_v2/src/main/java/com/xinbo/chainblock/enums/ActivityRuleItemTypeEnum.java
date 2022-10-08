package com.xinbo.chainblock.enums;

import cn.hutool.core.util.EnumUtil;
import com.xinbo.chainblock.bo.EnumItemBo;
import com.xinbo.chainblock.utils.LanguageUtil;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @date 8/9/22 10:21 下午
 * @desc 活动规则项类型
 */
@AllArgsConstructor
public enum ActivityRuleItemTypeEnum {
    RANGE(1,"600070", "范围"),
    EQUAL(2,"600071", "等于"),
    GREAT_THAN(3,"600072", "大于"),
    GREAT_THAN_EQUAL(4,"600073", "大于等于"),
    LESS_THAN(5,"600074", "小于"),
    LESS_THAN_EQUAL(6,"600075", "小于等于")
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
        for (ActivityRuleItemTypeEnum e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(e.getName()).nameZh(e.getNameZh()).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static EnumItemBo valueOfTranslate(int code) {
        for (ActivityRuleItemTypeEnum e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(LanguageUtil.translate(e.getName())).nameZh(e.getNameZh()).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static Map<Integer, EnumItemBo> toMap() {
        Map<Integer, EnumItemBo> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(ActivityRuleItemTypeEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), ActivityRuleItemTypeEnum.valueOfTranslate((int) code));
        }
        return map;
    }

    public static List<EnumItemBo> toList() {
        List<EnumItemBo> list = new ArrayList<>();
        List<Object> codes = EnumUtil.getFieldValues(ActivityRuleItemTypeEnum.class, "code");
        for (Object code : codes) {
            list.add(ActivityRuleItemTypeEnum.valueOfTranslate((int) code));
        }
        return list;
    }
}
