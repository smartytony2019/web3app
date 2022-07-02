package com.xinbo.chainblock.entity;

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
@TableName("t_recharge")
public class RechargeEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 转帐id
     */
    @TableField("transaction_id")
    private String transactionId;

    /**
     * 币种
     */
    @TableField("token_symbol")
    private String tokenSymbol;

    /**
     * 位数
     */
    @TableField("token_decimals")
    private Integer tokenDecimals;

    /**
     * 币种名
     */
    @TableField("token_name")
    private String tokenName;

    /**
     * 币种名
     */
    @TableField("token_address")
    private String tokenAddress;

    /**
     * 块时间戳
     */
    @TableField("block_timestamp")
    private Long blockTimestamp;

    /**
     * 转帐地址
     */
    @TableField("from_address")
    private String fromAddress;

    /**
     * 收款地址
     */
    @TableField("to_address")
    private String toAddress;

    /**
     * 转帐类型
     */
    private String type;

    /**
     * 转帐金额
     */
    private Double value;

}
