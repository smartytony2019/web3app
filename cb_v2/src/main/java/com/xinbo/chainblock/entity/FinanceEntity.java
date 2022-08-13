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
 * @desc 资金
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_finance")
public class FinanceEntity {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 用户名
     */
    @TableField("transaction_id")
    private String transactionId;

    /**
     * 用户名
     */
    @TableField("from_address")
    private String fromAddress;

    /**
     * 用户名
     */
    @TableField("to_address")
    private String toAddress;

    /**
     * 用户名
     */
    @TableField("money")
    private Float money;

    /**
     * 充值时间
     */
    @TableField("block_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date blockTime;


    /**
     * 充值时间戳
     */
    @TableField("block_timestamp")
    private Long blockTimestamp;

    /**
     * 币种
     */
    @TableField("symbol")
    private String symbol;

    /**
     * 类型(1:充值, 2:提现)
     */
    @TableField("type")
    private Integer type;

    /**
     * 是否统计
     */
    @TableField("is_account")
    private Boolean isAccount;


}
