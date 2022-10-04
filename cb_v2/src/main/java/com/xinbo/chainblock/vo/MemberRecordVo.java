package com.xinbo.chainblock.vo;

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
public class MemberRecordVo {

    private Integer id;

    /**
     * 帐号
     */
    private String username;

    /**
     * 域名
     */
    private String domain;

    /**
     * 设备
     */
    private String device;


    /**
     * 注册ip
     */
    private String regIp;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 登录ip
     */
    private String loginIp;


    /**
     * 登录时间
     */
    private Date loginTime;


    /**
     * 类型
     */
    private Integer type;

}
