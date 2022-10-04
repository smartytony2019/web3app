package com.xinbo.chainblock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tony
 * @date 6/24/22 4:21 下午
 * @desc 会员记录表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_member_record")
public class MemberRecordEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 帐号
     */
    @TableField("username")
    private String username;

    /**
     * 域名
     */
    @TableField("domain")
    private String domain;

    /**
     * 设备
     */
    @TableField("device")
    private String device;


    /**
     * 注册ip
     */
    @TableField("reg_ip")
    private String regIp;

    /**
     * 注册时间
     */
    @TableField("reg_time")
    private Date regTime;

    /**
     * 登录ip
     */
    @TableField("login_ip")
    private String loginIp;


    /**
     * 登录时间
     */
    @TableField("login_time")
    private Date loginTime;


    /**
     * 类型(1:登录 2:注册 3:签到)
     */
    @TableField("type")
    private Integer type;

}
