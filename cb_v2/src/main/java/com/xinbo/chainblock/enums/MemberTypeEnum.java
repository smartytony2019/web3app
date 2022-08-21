package com.xinbo.chainblock.enums;

import cn.hutool.core.util.EnumUtil;
import com.xinbo.chainblock.bo.EnumItem;
import com.xinbo.chainblock.utils.TranslateUtil;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @date 8/9/22 10:21 下午
 * @desc file desc
 */
@AllArgsConstructor
public enum MemberTypeEnum {
    NORMAL(1, "1", "正常会员"),
    TEST_ACCOUNT(2, "2", "测试帐号")
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

    public static EnumItem valueOf(int code) {
        for (MemberTypeEnum  e: values()) {
            if(e.getCode() == code){
                return  EnumItem.builder().code(code).name(String.valueOf(code)).nameZh(TranslateUtil.translate(e.getName())).build();
            }
        }
        return EnumItem.builder().build();
    }

    public static Map<Integer, EnumItem> toMap() {
        Map<Integer, EnumItem> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(MemberTypeEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), MemberTypeEnum.valueOf((int) code));
        }
        return map;
    }
}
