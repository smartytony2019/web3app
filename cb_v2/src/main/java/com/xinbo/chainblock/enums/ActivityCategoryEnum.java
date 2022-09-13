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
 * @desc 活动类目
 */
@AllArgsConstructor
public enum ActivityCategoryEnum {
    ALL(0,"600010", "全部活动"),
    LIMITED_TIME(1,"600011", "限时活动"),
    NEWCOMER(2,"600012", "不限次数"),
    DAILY(3,"600013", "日常活动")
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
        for (ActivityCategoryEnum e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(TranslateUtil.translate(e.getName())).nameZh(e.getNameZh()).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static Map<Integer, EnumItemBo> toMap() {
        Map<Integer, EnumItemBo> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(ActivityCategoryEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), ActivityCategoryEnum.valueOf((int) code));
        }
        return map;
    }

    public static List<EnumItemBo> toList() {
        List<EnumItemBo> list = new ArrayList<>();
        List<Object> codes = EnumUtil.getFieldValues(ActivityCategoryEnum.class, "code");
        for (Object code : codes) {
            list.add(ActivityCategoryEnum.valueOf((int) code));
        }
        return list;
    }
}
