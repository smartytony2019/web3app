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
 * @desc 活动规则限制项
 */
@AllArgsConstructor
public enum ActivityRuleCycleEnum {
    ONE_TIME(1,"500310", "一次"),
    UNLIMITED(2,"500320", "不限次数"),

    ONE_TIME_DAY(3,"500330", "一天一次"),
    ONE_TIME_WEEK(4,"500340", "一周一次"),
    ONE_TIME_MONTH(5,"500340", "一月一次"),
    CUSTOM(6,"500350", "自定义天数")
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
                return  EnumItemBo.builder().code(code).name(String.valueOf(code)).nameZh(TranslateUtil.translate(e.getName())).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static Map<Integer, EnumItemBo> toMap() {
        Map<Integer, EnumItemBo> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(ActivityRuleCycleEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), ActivityRuleCycleEnum.valueOf((int) code));
        }
        return map;
    }
}
