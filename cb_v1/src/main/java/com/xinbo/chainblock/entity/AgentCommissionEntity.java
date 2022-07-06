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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_agent_commission")
public class AgentCommissionEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String date;

    private Integer uid;

    private String username;

    private Double commission;

    @TableField("total_performance")
    private Double totalPerformance;

    @TableField("direct_performance")
    private Double directPerformance;

    @TableField("self_performance")
    private Double selfPerformance;

    @TableField("team_performance")
    private Double teamPerformance;

    private Integer rebate;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

}
