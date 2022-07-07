package com.xinbo.chainblock.jobs;

import com.xinbo.chainblock.core.algorithm.AlgorithmCode;
import com.xinbo.chainblock.core.algorithm.AlgorithmResult;
import com.xinbo.chainblock.core.algorithm.LotteryAlgorithm;
import com.xinbo.chainblock.entity.HashResultEntity;
import com.xinbo.chainblock.entity.LotteryBetEntity;
import com.xinbo.chainblock.service.HashResultService;
import com.xinbo.chainblock.service.LotteryBetService;
import com.xinbo.chainblock.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * @author tony
 * @date 7/1/22 5:08 下午
 * @desc 彩票结算任务
 */
@Slf4j
//@Component
public class LotterySettleJob {

    @Autowired
    private HashResultService hashResultService;

    @Autowired
    private LotteryBetService lotteryBetService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private LotteryAlgorithm lotteryAlgorithm;

    private static final int SIZE = 50;

    @Scheduled(cron = "0/5 * * * * ?")
    public void settle() {
        try {
            //Step 1: 拿未结算的开奖数据(t_hash_result.is_settle为0)
            HashResultEntity resultEntity = hashResultService.unsettle();
            if (ObjectUtils.isEmpty(resultEntity) || resultEntity.getId() <= 0) {
                return;
            }

            //Step 2: 到彩票注单表里面拿到未结算的注单
            List<LotteryBetEntity> betEntityList = lotteryBetService.unsettle(resultEntity.getNum(), SIZE);

            //Step 2.1: 如果全都已结算则更新开奖数据(t_hash_result.is_settle设置为1)
            if (CollectionUtils.isEmpty(betEntityList) || betEntityList.size() <= 0) {
                boolean isSuccess = hashResultService.settled(resultEntity.getId());
                if (!isSuccess) {
                    log.error("update hash result table fail");
                }
                return;
            }

            //Step 2.2: 计算输赢&构建数据
            for (LotteryBetEntity betEntity : betEntityList) {
                AlgorithmResult settle = lotteryAlgorithm.settle(resultEntity, betEntity);
                float profileMoney = 0, payoutMoney = 0;
                if (settle.getStatus() == AlgorithmCode.WIN) {
                    profileMoney = betEntity.getMoney() * betEntity.getOdds() - betEntity.getMoney();
                    payoutMoney = betEntity.getMoney() + profileMoney;
                } else {
                    profileMoney = betEntity.getMoney() * -1;
                    payoutMoney = 0;
                }
                betEntity.setProfitMoney(profileMoney);
                betEntity.setPayoutMoney(payoutMoney);
                betEntity.setHashResult(resultEntity.getBlockHash());
            }

            //Step 3: 更新数据库 @todo
            lotteryBetService.settle(betEntityList);


            System.out.println("@Scheduled" + new Date());
        } catch (RuntimeException ex) {
            log.error("settle: ", ex);
        }
    }


}
