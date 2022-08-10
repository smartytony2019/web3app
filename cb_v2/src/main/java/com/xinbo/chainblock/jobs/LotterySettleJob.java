package com.xinbo.chainblock.jobs;

import com.xinbo.chainblock.core.algorithm.AlgorithmCode;
import com.xinbo.chainblock.core.algorithm.AlgorithmResult;
import com.xinbo.chainblock.core.algorithm.HashAlgorithm;
import com.xinbo.chainblock.entity.hash.HashResultEntity;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.service.HashResultService;
import com.xinbo.chainblock.service.HashBetService;
import com.xinbo.chainblock.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
//@Component
public class LotterySettleJob {

    @Autowired
    private HashResultService hashResultService;

    @Autowired
    private HashBetService hashBetService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private HashAlgorithm hashAlgorithm;

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
//            List<HashBetEntity> hashBetEntityList = hashBetService.unsettle(resultEntity.getNum(), SIZE);
            List<HashBetEntity> hashBetEntityList = new ArrayList<>();

            //Step 2.1: 如果全都已结算则更新开奖数据(t_hash_result.is_settle设置为1)
            if (CollectionUtils.isEmpty(hashBetEntityList) || hashBetEntityList.size() <= 0) {
                boolean isSuccess = hashResultService.settled(resultEntity.getId());
                if (!isSuccess) {
                    log.error("update hash result table fail");
                }
                return;
            }

            //Step 2.2: 计算输赢&构建数据
            for (HashBetEntity hashBetEntity : hashBetEntityList) {
//                AlgorithmResult settle = hashAlgorithm.settle(resultEntity, hashBetEntity);
                AlgorithmResult settle = null;
                float profileMoney = 0, payoutMoney = 0;
                if (settle.getStatus() == AlgorithmCode.WIN) {
                    profileMoney = hashBetEntity.getMoney() * hashBetEntity.getOdds() - hashBetEntity.getMoney();
                    payoutMoney = hashBetEntity.getMoney() + profileMoney;
                } else {
                    profileMoney = hashBetEntity.getMoney() * -1;
                    payoutMoney = 0;
                }
                hashBetEntity.setProfitMoney(profileMoney);
                hashBetEntity.setPayoutMoney(payoutMoney);
                hashBetEntity.setHashBlockResult(resultEntity.getBlockHash());
            }

            //Step 3: 更新数据库 @todo
            hashBetService.settle(hashBetEntityList);


            System.out.println("@Scheduled" + new Date());
        } catch (RuntimeException ex) {
            log.error("settle: ", ex);
        }
    }


}
