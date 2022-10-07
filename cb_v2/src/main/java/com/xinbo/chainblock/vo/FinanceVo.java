package com.xinbo.chainblock.vo;

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
public class FinanceVo {


    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户名
     */
    private String transactionId;

    /**
     * 用户名
     */
    private String fromAddress;

    /**
     * 用户名
     */
    private String toAddress;

    /**
     * 用户名
     */
    private Float money;

    /**
     * 充值时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date blockTime;


    /**
     * 充值时间戳
     */
    private Long blockTimestamp;

    /**
     * 币种
     */
    private String symbol;

    /**
     * 类型(1:充值, 2:提现)
     */
    private Integer type;


}
