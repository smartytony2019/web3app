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
    private Float ratio;



}
