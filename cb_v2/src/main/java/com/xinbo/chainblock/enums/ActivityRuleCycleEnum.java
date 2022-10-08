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
 * @desc 活动规则限制项
 */
@AllArgsConstructor
public enum ActivityRuleCycleEnum {
    ONE_TIME(1,"600030", "一次"),
    UNLIMITED(2,"600031", "不限次数"),

    ONE_TIME_DAY(3,"600032", "一天一次"),
    ONE_TIME_WEEK(4,"600033", "一周一次"),
    ONE_TIME_MONTH(5,"600034", "一月一次"),
    CUSTOM(6,"600035", "自定义天数")
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
        for (ActivityRuleCycleEnum e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(e.getName()).nameZh(e.getNameZh()).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static EnumItemBo valueOfTranslate(int code) {
        for (ActivityRuleCycleEnum e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(LanguageUtil.translate(e.getName())).nameZh(e.getNameZh()).build();
            }
        }
        return EnumItemBo.builder().build();
    }


    public static Map<Integer, EnumItemBo> toMap() {
        Map<Integer, EnumItemBo> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(ActivityRuleCycleEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), ActivityRuleCycleEnum.valueOfTranslate((int) code));
        }
        return map;
    }

    public static List<EnumItemBo> toList() {
        List<EnumItemBo> list = new ArrayList<>();
        List<Object> codes = EnumUtil.getFieldValues(ActivityRuleCycleEnum.class, "code");
        for (Object code : codes) {
            list.add(ActivityRuleCycleEnum.valueOfTranslate((int) code));
        }
        return list;
    }
}
