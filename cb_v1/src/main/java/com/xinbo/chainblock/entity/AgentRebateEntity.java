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
@TableName("t_agent_rebate")
public class AgentRebateEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 最低业绩
     */
    private Integer min;
    /**
     * 最高业绩
     */
    private Integer max;
    /**
     * 回扣
     */
    private Integer rebate;


}
