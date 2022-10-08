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
 * @desc 计算方式
 */
@AllArgsConstructor
public enum HashBetStatusEnum {
    UNSETTLED(0,"401010", "未结算"),
    SETTLED(1,"401011", "已结算"),
    REJECT(2,"401012", "作废")
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
        for (HashBetStatusEnum e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(e.getName()).nameZh(e.getNameZh()).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static EnumItemBo valueOfTranslate(int code) {
        for (HashBetStatusEnum e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(LanguageUtil.translate(e.getName())).nameZh(e.getNameZh()).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static Map<Integer, EnumItemBo> toMap() {
        Map<Integer, EnumItemBo> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(HashBetStatusEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), HashBetStatusEnum.valueOfTranslate((int) code));
        }
        return map;
    }

    public static List<EnumItemBo> toList() {
        List<EnumItemBo> list = new ArrayList<>();
        List<Object> codes = EnumUtil.getFieldValues(HashBetStatusEnum.class, "code");
        for (Object code : codes) {
            list.add(HashBetStatusEnum.valueOfTranslate((int) code));
        }
        return list;
    }
}
