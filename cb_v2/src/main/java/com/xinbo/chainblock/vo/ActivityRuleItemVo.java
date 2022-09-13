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
@TableName("t_activity_rule_item")
public class ActivityRuleItemVo {

    /**
     * 编号
     */
    private String sn;


    /**
     * 类型
     */
    private Integer type;


    /**
     * 最小值
     */
    private Integer min;


    /**
     * 最大值
     */
    private Integer max;


    /**
     * 赠送比例
     */
    private Float ratio;



}
