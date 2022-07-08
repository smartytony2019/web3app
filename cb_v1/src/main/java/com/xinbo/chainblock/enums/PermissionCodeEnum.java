package com.xinbo.chainblock.enums;

import cn.hutool.core.util.EnumUtil;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
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

    USER_ADD(10011, "会员添加"),
    USER_DEL(10012, "会员删除"),
    USER_EDIT(10013, "会员修改"),
    USER_FIND(10014, "会员查找"),

    USER_FLOW_ADD(10011, "会员流水添加"),
    USER_FLOW_DEL(10012, "会员流水删除"),
    USER_FLOW_EDIT(10013, "会员流水修改"),
    USER_FLOW_FIND(10014, "会员流水查找"),

    Merchant(2, "商户管理员");

    int code;
    String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static PermissionCodeEnum valueOf(int code) {
        for (PermissionCodeEnum e : PermissionCodeEnum.values()) {
            if (code == e.getCode()) {
                return e;
            }
        }
        return null;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer,String> map = new HashMap<>();
        List<Object> codes = EnumUtil.getFieldValues(PermissionCodeEnum.class, "code");
        for (Object code : codes) {
            map.put(Integer.parseInt(code.toString()), PermissionCodeEnum.valueOf((int) code).getMsg());
        }
        return map;
    }
}
