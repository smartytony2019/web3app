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
 * @date 7/8/22 2:16 下午
 * @desc file desc
 */
@AllArgsConstructor
public enum PermissionCodeEnum {

    USER_ADD(500310,"500310", "会员添加"),
    USER_DEL(500311, "500311", "会员删除"),
    USER_EDIT(500312,"500312",  "会员修改"),
    USER_FIND(500313,"500313",  "会员查找"),

    USER_FLOW_ADD(500320,"500320",  "会员流水添加"),
    USER_FLOW_DEL(500321,"500321", "会员流水删除"),
    USER_FLOW_EDIT(500322, "500322","会员流水修改"),
    USER_FLOW_FIND(500323, "500323","会员流水查找"),

    Merchant(500330, "500330","商户管理员");

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
        for (PermissionCodeEnum  e: values()) {
            if(e.getCode() == code){
                return  EnumItemBo.builder().code(code).name(String.valueOf(code)).nameZh(TranslateUtil.translate(e.getName())).build();
            }
        }
        return EnumItemBo.builder().build();
    }

    public static Map<Integer, EnumItemBo> toMap() {
        Map<Integer, EnumItemBo> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(PermissionCodeEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), PermissionCodeEnum.valueOf((int) code));
        }
        return map;
    }
}
