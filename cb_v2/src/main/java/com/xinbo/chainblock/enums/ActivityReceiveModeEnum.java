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
 * @desc 领取方式
 */
@AllArgsConstructor
public enum ActivityReceiveModeEnum {
    SEND(1,"600050", "直接发放"),
    CHECK(2,"600051", "后端审核"),
    AUTO(3,"600052", "自动发放")
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
        for (ActivityReceiveModeEnum e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(e.getName()).nameZh(e.getNameZh()).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static EnumItemBo valueOfTranslate(int code) {
        for (ActivityReceiveModeEnum e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(TranslateUtil.translate(e.getName())).nameZh(e.getNameZh()).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static Map<Integer, EnumItemBo> toMap() {
        Map<Integer, EnumItemBo> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(ActivityReceiveModeEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), ActivityReceiveModeEnum.valueOfTranslate((int) code));
        }
        return map;
    }

    public static List<EnumItemBo> toList() {
        List<EnumItemBo> list = new ArrayList<>();
        List<Object> codes = EnumUtil.getFieldValues(ActivityReceiveModeEnum.class, "code");
        for (Object code : codes) {
            list.add(ActivityReceiveModeEnum.valueOfTranslate((int) code));
        }
        return list;
    }
}
