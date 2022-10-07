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
 * @desc 资金审计
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_finance_audit")
public class FinanceAuditEntity {


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
     * 订单号
     */
    @TableField("sn")
    private String sn;

    /**
     * 打码量
     */
    @TableField("amount")
    private Float amount;

    /**
     * 之前打码量
     */
    @TableField("before_amount")
    private Float beforeAmount;

    /**
     * 要求打码量
     */
    @TableField("require_amount")
    private Float requireAmount;

    /**
     * 完成打码量
     */
    @TableField("finish_amount")
    private Float finishAmount;

    /**
     * 状态(1:已完成,0:未完成)
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
