package com.xinbo.chainblock.entity.activity;

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
 * @date 6/25/22 4:10 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_activity_rule")
public class ActivityRuleEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 编号
     */
    @TableField("sn")
    private String sn;

    /**
     * 活动id
     */
    @TableField("activity_id")
    private Integer activityId;

    /**
     * 周期(1:一次 2:不限次数 3:一天一次 4:一周一次 5:一月一次 6:自定义天数)
     */
    @TableField("cycle")
    private Integer cycle;


    /**
     * 天数
     */
    @TableField("days")
    private Integer days;

    /**
     * 限制项(1:钱包, 2:充值, 3:首充, 4:打码, 4:注册, 5:注册送)
     */
    @TableField("limit_item")
    private Integer limitItem;


    /**
     * 限制等级(1: 包含项, 2: 必须项)
     */
    @TableField("limit_lev")
    private Integer limitLev;


    /**
     * 提现打码倍数
     */
    @TableField("withdraw_bet_mul")
    private String withdrawBetMul;


    /**
     * 计算方式(1:固定金额 2:百分比)
     */
    @TableField("calc_mode")
    private Integer calcMode;


    /**
     * 领取方式(1:后端审核, 2:自动发放)
     */
    @TableField("receive_method")
    private Integer receiveMode;


    /**
     * 金额
     */
    @TableField("money")
    private Float money;


    /**
     * 赠送币种
     */
    @TableField("symbol")
    private String symbol;

}
