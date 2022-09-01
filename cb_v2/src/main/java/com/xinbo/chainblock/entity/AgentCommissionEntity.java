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

    /**
     * 日期
     */
    @TableField("date")
    private String date;

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
     * 总佣金
     */
    @TableField("total_commission")
    private Float totalCommission;

    /**
     * 总业绩
     */
    @TableField("total_performance")
    private Float totalPerformance;

    /**
     * 自营佣金
     */
    @TableField("self_commission")
    private Float selfCommission;

    /**
     *自营业绩
     */
    @TableField("self_performance")
    private Float selfPerformance;

    /**
     * 直属业绩
     */
    @TableField("direct_performance")
    private Float directPerformance;

    /**
     * 下属业绩
     */
    @TableField("sub_performance")
    private Float subPerformance;

    /**
     * 团队业绩
     */
    @TableField("team_performance")
    private Float teamPerformance;

    /**
     * 返佣比
     */
    @TableField("rebate")
    private Integer rebate;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建时间戳
     */
    @TableField("create_timestamp")
    private Long createTimestamp;

    /**
     * 是否入帐
     */
    @TableField("is_account")
    private Boolean isAccount;

}
