package com.xinbo.chainblock.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tony
 * @date 6/25/22 4:10 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityRuleVo {


    /**
     * 编号
     */
    private String sn;

    /**
     * 活动id
     */
    private Integer activityId;

    /**
     * 周期(1:一次 2:不限次数 3:一天一次 4:一周一次 5:一月一次 6:自定义天数)
     */
    private Integer cycle;


    /**
     * 天数
     */
    private Integer days;

    /**
     * 限制项(1:钱包, 2:充值, 3:首充, 4:打码, 4:注册, 5:注册送)
     */
    private Integer limitItem;


    /**
     * 限制等级(1: 包含项, 2: 必须项)
     */
    private Integer limitLev;


    /**
     * 提现打码倍数
     */
    private String withdrawBetMul;


    /**
     * 彩金打码倍数
     */
    private String jackpotBetMul;


    /**
     * 计算方式(1:固定金额 2:百分比)
     */
    private Integer calcMode;


    /**
     * 领取方式(1:后端审核, 2:自动发放)
     */
    private Integer receiveMode;


    /**
     * 金额
     */
    private Float money;


    /**
     * 赠送币种
     */
    private String symbol;

}
