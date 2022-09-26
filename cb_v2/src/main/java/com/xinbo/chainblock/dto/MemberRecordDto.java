package com.xinbo.chainblock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class MemberRecordDto {

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regTime;

    /**
     * 登录ip
     */
    private String loginIp;


    /**
     * 登录时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;


    /**
     * 类型(1:登录 2:注册 3:签到)
     */
    private Integer type;

}
