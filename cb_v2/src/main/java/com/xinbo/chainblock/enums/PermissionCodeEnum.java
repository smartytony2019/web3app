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

    USER_ADD(500310,"500310", "用户添加"),
    USER_DEL(500311, "500311", "用户删除"),
    USER_EDIT(500312,"500312",  "用户修改"),
    USER_FIND(500313,"500313",  "用户查找"),

    USER_FLOW_ADD(500320,"500320",  "会员流水添加"),
    USER_FLOW_DEL(500321,"500321", "会员流水删除"),  //Y
    USER_FLOW_EDIT(500322, "500322","会员流水修改"),
    USER_FLOW_FIND(500323, "500323","会员流水查找"),

    ACTIVITY_operate(500324,"500324","活动操作"),
    ACTIVITY_DEL(500325,"500325","活动删除"),
    ACTIVITY_FIND(500326,"500326","活动查找"),


    activity_rule_add(500327,"500327","活动规则添加"),

    notice_add(500328,"500328","公告添加"),
    notice_del(500329,"500329","公告删除"),
    notice_Edit(500330,"500330","公告修改"),
    notice_find(500331,"500331","公告查询"),

    banner_add(500332,"500332","轮播图添加"),
    banner_del(500333,"500333","轮播图删除"),
    banner_edit(500334,"500334","轮播图修改"),
    banner_find(500335,"500335","轮播图查找"),

    language_add(500336,"500336","语言添加"),
    language_del(500337,"500337","语言删除"),
    language_edit(500338,"500338","语言修改"),
    language_find(500339,"500339","语言查找"),

    role_add(500340,"500340","角色添加"),
    role_del(500341,"500341","角色删除"),
    role_edit(500342,"500342","角色修改"),

    permission_add(500343,"500343","权限添加"),
    permission_del(500344,"500344","权限删除"),
    permission_edit(500345,"500345","权限修改"),

    role_permission_add(500346,"500346","分配权限"),

    user_role_add(500347,"500347","分配角色"),

    user_password_edit(500348,"500348","修改密码"),

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
