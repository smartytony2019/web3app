package com.xinbo.chainblock.jobs;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinbo.chainblock.bo.AlgorithmResult;
import com.xinbo.chainblock.bo.HashResultApiBo;
import com.xinbo.chainblock.consts.RedisConst;
import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.core.algorithm.AlgorithmCode;
import com.xinbo.chainblock.core.algorithm.HashAlgorithm;
import com.xinbo.chainblock.core.algorithm.HashOfflineAlgorithm;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.hash.HashOfflineBetEntity;
import com.xinbo.chainblock.entity.hash.HashResultEntity;
import com.xinbo.chainblock.enums.SystemFlowItemEnum;
import com.xinbo.chainblock.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tony
 * @date 6/25/22 2:59 下午
 * @desc 离线注单任务
 */
@Slf4j
@Component
public class HashOfflineBetJob {

    @Autowired
    private TrxApi trxApi;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${trx.symbol}")
    private String trxSymbol;

    @Value("${trx.token-info.symbol}")
    private String tokenSymbol;

    @Value("${trx.token-info.name}")
    private String tokenName;

    @Value("${trx.network}")
    private String network;

    @Autowired
    private GameService gameService;

    @Autowired
    private HashOfflineBetService hashOfflineBetService;

    @Autowired
    private HashOfflineAlgorithm hashOfflineAlgorithm;

    @Value("${scheduled.enable.offline.bet}")
    private boolean isOfflineBet;

    @Value("${scheduled.enable.offline.settle}")
    private boolean isOfflineSettle;


    /**
     * 处理投注记录
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void handleBetRecord() {
        try {
            if (!isOfflineBet) {
                return;
            }

            List<GameEntity> games = gameService.findOffline();
            if (CollectionUtils.isEmpty(games)) {
                return;
            }

            try {
                for (GameEntity entity : games) {
                    long minTimestamp = new Date().getTime() - (3 * 60 * 60 * 1000);
                    JSONObject trc20Record = trxApi.getTrc20Record(entity.getAddress(), minTimestamp);
                    if (ObjectUtils.isEmpty(trc20Record)) {
                        continue;
                    }
                    JSONArray trc20Data = trc20Record.getJSONArray("data");
                    if (!ObjectUtils.isEmpty(trc20Data)) {
                        List<HashOfflineBetEntity> bets = new ArrayList<>();
                        for (int i = 0; i < trc20Data.size(); i++) {
                            JSONObject jsonObject = trc20Data.getJSONObject(i);
                            if (ObjectUtils.isEmpty(jsonObject)) {
                                continue;
                            }

                            String transactionId = jsonObject.getString("transaction_id");
                            long blockTimestamp = jsonObject.getLong("block_timestamp");
                            String fromAddress = jsonObject.getString("from");
                            String toAddress = jsonObject.getString("to");
                            BigInteger value = jsonObject.getBigInteger("value");

                            JSONObject tokenInfo = jsonObject.getJSONObject("token_info");
                            String symbol = tokenInfo.getString("symbol");
                            int decimals = tokenInfo.getInteger("decimals");
                            String name = tokenInfo.getString("name");

                            if (!toAddress.equals(entity.getAddress())) {
                                continue;
                            }
                            if (!symbol.equals(tokenSymbol)) {
                                continue;
                            }

                            BigDecimal b1 = new BigDecimal(value);
                            BigDecimal b2 = new BigDecimal(String.format("%s", Math.pow(10, decimals)));
                            BigDecimal b3 = b1.divide(b2, 2, RoundingMode.DOWN);


                            String sn = IdUtil.getSnowflake().nextIdStr();
                            HashOfflineBetEntity bet = HashOfflineBetEntity.builder()
                                    .sn(sn)
                                    .cateId(entity.getCateId())
                                    .cateName(entity.getCateName())
                                    .cateNameZh(entity.getCateNameZh())
                                    .gameId(entity.getId())
                                    .gameName(entity.getName())
                                    .gameNameZh(entity.getNameZh())

                                    .transactionId(transactionId)
                                    .network(network)
                                    .odds(entity.getOdds())
                                    .money(b3.floatValue())
                                    .createTime(DateUtil.date(blockTimestamp))
                                    .createTimestamp(DateUtil.current())
                                    .algorithm(entity.getAlgorithm())
                                    .build();
                            bets.add(bet);
                        }

                        if (bets.size() > 0) {
                            boolean isSuccess = hashOfflineBetService.batchInsert(bets);
                            if (!isSuccess) {
                                log.info("OfflineBetJob batch insert failure");
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
//                threadPool.shutdown();
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * 处理结算注单
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void handleSettleBet() {
        try {
            if (!isOfflineSettle) {
                return;
            }

            List<HashOfflineBetEntity> list = hashOfflineBetService.unsettle();
            for (HashOfflineBetEntity bet : list) {
                HashResultApiBo result = trxApi.getBlockHashByTransactionId(bet.getTransactionId());
                if (ObjectUtils.isEmpty(result) || StringUtils.isEmpty(result.getBlockHash())) {
                    continue;
                }

                // 算法计算是否中奖
                HashResultEntity resultEntity = HashResultEntity.builder()
                        .blockHash(result.getBlockHash())
                        .blockHeight(result.getBlockHeight())
                        .build();
                AlgorithmResult algorithmResult = hashOfflineAlgorithm.settle(resultEntity, bet);

                // 如果中奖则派彩
                float profileMoney = 0, payoutMoney = 0;
                if (algorithmResult.getStatus() == AlgorithmCode.WIN) {
                    profileMoney = bet.getMoney() * bet.getOdds() - bet.getMoney();
                    payoutMoney = bet.getMoney() + profileMoney;
                } else if (algorithmResult.getStatus() == AlgorithmCode.LOST) {
                    profileMoney = bet.getMoney() * -1;
                    payoutMoney = 0;
                } else {
                }

                // 系统帐变 & 注单状态
                bet.setFlag(algorithmResult.getStatus());
                bet.setStatus(1);
                bet.setUpdateTime(DateUtil.date());
                bet.setUpdateTimestamp(DateUtil.current());
                bet.setProfitMoney(profileMoney);
                bet.setPayoutMoney(payoutMoney);
                bet.setBlockHash(result.getBlockHash());
                bet.setBlockHeight(result.getBlockHeight());


                boolean isSuccess = hashOfflineBetService.settle(bet);
                if (isSuccess && algorithmResult.getStatus() == AlgorithmCode.WIN) {
                    SystemFlowEntity flowEntity = SystemFlowEntity.builder()
                            .sn(bet.getSn())
                            .item(SystemFlowItemEnum.HASH_BET.getName())
                            .itemCode(SystemFlowItemEnum.HASH_BET.getCode())
                            .itemZh(SystemFlowItemEnum.HASH_BET.getNameZh())
                            .flowMoney(payoutMoney)
                            .createTime(DateUtil.date())
                            .create_timestamp(DateUtil.current())
                            .build();
                    redisTemplate.opsForSet().add(RedisConst.SYSTEM_FLOW, JSON.toJSONString(flowEntity));
                }

            }
        } catch (Exception ex) {
            log.error("handleSettleBet exception", ex);
        }


    }

}
















