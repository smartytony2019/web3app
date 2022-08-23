package com.xinbo.chainblock.entity.activity;

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
@TableName("t_activity_rule_item")
public class ActivityRuleItemEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 规则id
     */
    @TableField("rule_id")
    private Integer rule_id;

    /**
     * 限制项(1:钱包, 2:充值, 3:首充, 4:打码, 4:注册, 5:注册送)
     */
    @TableField("limit_item")
    private Integer limit_item;


    /**
     * 限制等级(1: 包含项, 2: 必须项)
     */
    @TableField("limit_lev")
    private Integer limit_lev;


    /**
     * 类型
     */
    @TableField("type")
    private Integer type;


    /**
     * 最小值
     */
    @TableField("min")
    private Integer min;


    /**
     * 最大值
     */
    @TableField("max")
    private Integer max;


    /**
     * 赠送比例
     */
    @TableField("ratio")
    private Integer ratio;


    /**
     * 赠送币种
     */
    @TableField("symbol")
    private String symbol;



}
