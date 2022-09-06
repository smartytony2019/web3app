package com.xinbo.chainblock.jobs;

import com.xinbo.chainblock.bo.AccountApiBo;
import com.xinbo.chainblock.bo.AlgorithmResult;
import com.xinbo.chainblock.bo.HashResultApiBo;
import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.core.algorithm.AlgorithmCode;
import com.xinbo.chainblock.core.algorithm.HashAlgorithm;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.hash.HashResultEntity;
import com.xinbo.chainblock.service.HashBetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author tony
 * @date 7/1/22 5:08 下午
 * @desc 哈希注单结算任务
 */
@Slf4j
@Component
public class HashBetSettleJob {

    @Autowired
    private HashBetService hashBetService;

    @Autowired
    private TrxApi trxApi;

    @Autowired
    private HashAlgorithm hashAlgorithm;

    @Value("${scheduled.enable.settle}")
    private boolean isSettle;

    @Value("${trx.token-info.symbol}")
    private String symbol;


    @Scheduled(cron = "0/2 * * * * ?")
    public void settle() {
        try {
            if (!isSettle) {
                return;
            }

            // Step 1: 未结算数据
            HashBetEntity bet = hashBetService.unsettle();
            if(ObjectUtils.isEmpty(bet) || bet.getId() <= 0) {
                return;
            }

            // Step 2: 生成开奖
            HashResultApiBo hashResult = trxApi.resultFind(bet.getSn());
            if(ObjectUtils.isEmpty(hashResult) || StringUtils.isEmpty(hashResult.getBlockHash())) {
                return;
            }
            HashResultEntity result = HashResultEntity.builder()
                    .sn(hashResult.getSn())
                    .txID(hashResult.getTxID())
                    .blockHash(hashResult.getBlockHash())
                    .blockHeight(hashResult.getBlockHeight())
                    .openTime(hashResult.getOpenTime())
                    .openTimestamp(hashResult.getOpenTimestamp())
                    .network(hashResult.getNetwork())
                    .build();


            // Step 3: 结算
            AlgorithmResult algorithmResult = hashAlgorithm.settle(result, bet);
            float profileMoney = 0, payoutMoney = 0;
            if(algorithmResult.getStatus() == AlgorithmCode.WIN) {
                profileMoney = bet.getMoney() * bet.getOdds() - bet.getMoney();
                payoutMoney = bet.getMoney() + profileMoney;
            } else if(algorithmResult.getStatus() == AlgorithmCode.LOST) {
                profileMoney = bet.getMoney() * -1;
                payoutMoney = 0;
            } else {
            }
            result.setFlag(algorithmResult.getStatus());

            bet.setFlag(algorithmResult.getStatus());
            bet.setStatus(1);
            bet.setProfitMoney(profileMoney);
            bet.setPayoutMoney(payoutMoney);
            bet.setTransactionId(result.getTxID());
            bet.setBlockHash(result.getBlockHash());
            bet.setBlockHeight(result.getBlockHeight());
            bet.setNetwork(result.getNetwork());
            bet.setUpdateTime(new Date());


            // Step 4: 数据库操作
            hashBetService.settle(bet, result);

        } catch (RuntimeException ex) {
            log.error("settle: ", ex);
        }
    }


}
