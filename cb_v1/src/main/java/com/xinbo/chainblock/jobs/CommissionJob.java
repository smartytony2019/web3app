package com.xinbo.chainblock.jobs;

import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tony
 * @date 7/4/22 6:39 下午
 * @desc 代理佣金任务
 */

@Component
public class CommissionJob {


    @Autowired
    private AgentService agentService;


    /**
     * 结算佣金
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void settleCommission() {

        //Step 1: 获取代理层级表
        List<AgentEntity> list = agentService.findAll(0,500);
        System.out.println(list);




    }


}
