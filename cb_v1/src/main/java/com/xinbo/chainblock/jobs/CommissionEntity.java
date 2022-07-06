package com.xinbo.chainblock.jobs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommissionEntity {

    private Integer uid;
    private String username;
    private Double betMoney;
    private Integer rebate;

    private Double commission;

    private Double totalPerformance;

    /**
     * 自营业绩
     */
    private Double selfPerformance;

    /**
     * 团队业绩
     */
    private Double teamPerformance;



}
