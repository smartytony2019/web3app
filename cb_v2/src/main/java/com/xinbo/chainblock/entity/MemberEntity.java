package com.xinbo.chainblock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tony
 * @date 6/24/22 4:21 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_member")
public class MemberEntity {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 帐号
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("pwd")
    private String pwd;

    /**
     * 金额
     */
    @TableField("money")
    private Float money;


    /**
     * 盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 提现钱包地址
     */
    @TableField("withdraw_wallet")
    private Float withdraw_wallet;

    /**
     * 提现钱包密码
     */
    @TableField("withdraw_pwd")
    private Float withdraw_pwd;


    /**
     * 版本
     */
    @TableField("version")
    private Integer version;

}
