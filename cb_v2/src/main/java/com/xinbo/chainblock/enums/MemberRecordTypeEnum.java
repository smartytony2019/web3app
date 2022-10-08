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
 * @desc file desc
 */
@AllArgsConstructor
public enum MemberRecordTypeEnum {
    LOGIN(1, "700110", "登录"),
    REGISTER(2, "700111", "注册"),
    SIGN(3, "700112", "签到")
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
        for (MemberRecordTypeEnum e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(e.getName()).nameZh(e.getNameZh()).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static EnumItemBo valueOfTranslate(int code) {
        for (MemberRecordTypeEnum e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(LanguageUtil.translate(e.getName())).nameZh(e.getNameZh()).build();
            }
        }
        return EnumItemBo.builder().build();
    }


    public static Map<Integer, EnumItemBo> toMap() {
        Map<Integer, EnumItemBo> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(MemberRecordTypeEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), MemberRecordTypeEnum.valueOfTranslate((int) code));
        }
        return map;
    }

    public static List<EnumItemBo> toList() {
        List<EnumItemBo> list = new ArrayList<>();
        List<Object> codes = EnumUtil.getFieldValues(MemberRecordTypeEnum.class, "code");
        for (Object code : codes) {
            list.add(MemberRecordTypeEnum.valueOfTranslate((int) code));
        }
        return list;
    }
}
