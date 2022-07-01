package com.xinbo.chainblock.jobs;

import com.xinbo.chainblock.entity.HashResultEntity;
import com.xinbo.chainblock.entity.LotteryBetEntity;
import com.xinbo.chainblock.service.HashResultService;
import com.xinbo.chainblock.service.LotteryBetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * @author tony
 * @date 7/1/22 5:08 下午
 * @desc 彩票结算任务
 */
@Component
public class LotterySettleJob {

    @Autowired
    private HashResultService hashResultService;

    @Autowired
    private LotteryBetService lotteryBetService;

    private static final int SIZE = 50;

    @Scheduled(cron = "0/5 * * * * ?")
    public void settle() {
        try {

            //Step 1: 拿未结算的开奖数据(t_hash_result.is_settle为0)
            HashResultEntity resultEntity = hashResultService.unsettle();
            if(ObjectUtils.isEmpty(resultEntity) || resultEntity.getId() <=0) {
                return;
            }

            //Step 2: 到彩票注单表里面拿到未结算的注单
            List<LotteryBetEntity> betEntity = lotteryBetService.unsettle(resultEntity.getNum(), SIZE);
            System.out.println(betEntity);

            //Step 2.1: 如果全都已结算则更新开奖数据(t_hash_result.is_settle设置为1)
            //Step 2.2: 计算输赢

            //Step 3: 更新数据库

            System.out.println("@Scheduled"+new Date());
        }catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
