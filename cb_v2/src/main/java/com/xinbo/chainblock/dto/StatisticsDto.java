package com.xinbo.chainblock.dto;

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
 * @date 6/25/22 4:10 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_statistics")
public class StatisticsDto {

    /**
     * 日期
     */
    private String date;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 投注总额
     */
    private Float betAmount;

    /**
     * 当日投注次数
     */
    private Integer betCount;

    /**
     * 当日盈利总额
     */
    private Float profitAmount;

    /**
     * 当日中奖总额
     */
    private Float payoutAmount;

    /**
     * 充值trc20次数
     */
    private Integer rechargeTrc20Count;

    /**
     * 充值trc20总额
     */
    private Float rechargeTrc20Amount;

    /**
     * 提现trc20总额
     */
    private Float withdrawTrc20Amount;

    /**
     * 充值trx次数
     */
    private Integer rechargeTrxCount;

    /**
     * 充值trx总额
     */
    private Float rechargeTrxAmount;

    /**
     * 提现trx总额
     */
    private Float withdrawTrxAmount;

    /**
     * 佣金总额
     */
    private Float commissionAmount;

    /**
     * 活动总额
     */
    private Float activityAmount;

}
