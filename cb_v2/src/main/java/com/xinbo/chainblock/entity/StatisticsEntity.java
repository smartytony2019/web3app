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
 * @date 6/25/22 4:10 下午
 * @desc file desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_statistics")
public class StatisticsEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 投注金额
     */
    @TableField("bet_money")
    private Float betMoney;

    /**
     * 投注赢利金额
     */
    @TableField("bet_profit_money")
    private Float betProfitMoney;

    /**
     * 投注派彩金额
     */
    @TableField("bet_payout_money")
    private Float betPayoutMoney;

    /**
     * 充值金额
     */
    @TableField("recharge_money")
    private Float rechargeMoney;

    /**
     * 提现金额
     */
    @TableField("withdraw_money")
    private Float withdrawMoney;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

}
