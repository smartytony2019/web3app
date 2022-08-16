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
 * @desc 转换
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_transfer")
public class TransferEntity {


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
     * 金额
     */
    @TableField("money")
    private Float money;


    /**
     * 类型 1:资金帐户 => 交易帐户 2:交易帐户 => 资金帐户
     */
    @TableField("type")
    private Integer type;

    /**
     * 过期时间
     */
    @TableField("expired")
    private Long expired;

    /**
     * 状态(0:未确认, 1:确认, 2:过期)
     */
    @TableField("status")
    private Integer status;

}
