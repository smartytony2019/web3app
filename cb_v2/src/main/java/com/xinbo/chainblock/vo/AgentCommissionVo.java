package com.xinbo.chainblock.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentCommissionVo {

    private Integer id;

    /**
     * 日期
     */
    private String date;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 总佣金
     */
    private Float totalCommission;

    /**
     * 总业绩
     */
    private Float totalPerformance;

    /**
     * 自营佣金
     */
    private Float selfCommission;

    /**
     *自营业绩
     */
    private Float selfPerformance;

    /**
     * 团队业绩
     */
    private Float teamPerformance;

    /**
     * 团队人数
     */
    private Integer teamCount;

    /**
     * 直属人数
     */
    private Integer directCount;

    /**
     * 直属业绩
     */
    private Float directPerformance;

    /**
     * 下属业绩
     */
    private Float subPerformance;

    /**
     * 返佣比
     */
    private Integer rebate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建时间戳
     */
    private Long createTimestamp;

    /**
     * 是否入帐
     */
    private Boolean isAccount;

}
