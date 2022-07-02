package com.xinbo.chainblock.jobs;

import com.xinbo.chainblock.core.algorithm.AlgorithmCode;
import com.xinbo.chainblock.core.algorithm.AlgorithmResult;
import com.xinbo.chainblock.core.algorithm.LotteryAlgorithm;
import com.xinbo.chainblock.entity.HashResultEntity;
import com.xinbo.chainblock.entity.LotteryBetEntity;
import com.xinbo.chainblock.entity.UserEntity;
import com.xinbo.chainblock.service.HashResultService;
import com.xinbo.chainblock.service.LotteryBetService;
import com.xinbo.chainblock.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tony
 * @date 7/1/22 5:08 下午
 * @desc 彩票结算任务
 */
@Slf4j
@Component
public class LotterySettleJob {

    @Autowired
    private HashResultService hashResultService;

    @Autowired
    private LotteryBetService lotteryBetService;

    @Autowired
    private UserService userService;

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

            //Step 2.2: 计算输赢
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

            //Step 3: 构建数据 @todo
            System.out.println(betEntityList);
//            boolean isSuccess = lotteryBetService.settle(betEntityList);


            //Step 4: 更新数据库 @todo

            System.out.println("@Scheduled" + new Date());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    @Transactional
    public boolean settle(List<LotteryBetEntity> bets) {

        for (LotteryBetEntity bet : bets) {
            //更新注单表
            boolean isSuccess = lotteryBetService.settle(bet);

            //添加帐变

            //更新会员金额
            UserEntity userEntity = userService.findById(bet.getId());
            userEntity.setMoney(bet.getPayoutMoney());
            userService.increment(userEntity);
        }

        return false;
    }

}
