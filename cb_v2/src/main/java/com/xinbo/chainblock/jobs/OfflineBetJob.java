package com.xinbo.chainblock.jobs;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

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
public class OfflineBetJob {

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
    private HashBetService hashBetService;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private WalletService walletService;

    @Value("${scheduled.enable.offline.record}")
    private boolean isOfflineRecord;

    @Value("${scheduled.enable.offline.account}")
    private boolean isOfflineAccount;


    /**
     * 处理记录
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void handleRecord() {
        try {
            if (!isOfflineRecord) {
                return;
            }

            List<GameEntity> games = gameService.findOffline();
            if (CollectionUtils.isEmpty(games)) {
                return;
            }


//            // 创建线程池
//            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
//                    2,
//                    5,
//                    2L,
//                    TimeUnit.SECONDS,
//                    new ArrayBlockingQueue<>(games.size()),
//                    Executors.defaultThreadFactory(),
//                    new ThreadPoolExecutor.AbortPolicy()
//            );


            try {
                for (GameEntity entity : games) {
//                    WorkerThread wt = new WorkerThread(entity);
//                    threadPool.execute(wt);

                    long minTimestamp = new Date().getTime() - (3 * 60 * 60 * 1000);
                    JSONObject trc20Record = trxApi.getTrc20Record(entity.getAddress(), minTimestamp);
                    JSONArray trc20Data = trc20Record.getJSONArray("data");
                    if (!ObjectUtils.isEmpty(trc20Data)) {
                        List<HashBetEntity> bets = new ArrayList<>();
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


                            BigDecimal b1 = new BigDecimal(value);
                            BigDecimal b2 = new BigDecimal(String.format("%s", Math.pow(10, decimals)));
                            BigDecimal b3 = b1.divide(b2, 2, RoundingMode.DOWN);


                            HashBetEntity bet = HashBetEntity.builder()
                                    .cateId(entity.getCateId())
                                    .cateName(entity.getCateName())
                                    .cateNameZh(entity.getCateNameZh())
                                    .gameId(entity.getId())
                                    .gameName(entity.getName())
                                    .gameNameZh(entity.getNameZh())

                                    .transactionId(transactionId)
                                    .network(network)
                                    .odds(entity.getOdds())
                                    .moneyAmount(b3.floatValue())
                                    .createTime(DateUtil.date(blockTimestamp))
                                    .isOffline(true)
                                    .algorithm(entity.getAlgorithm())
                                    .build();
                            bets.add(bet);
                        }

                        if (bets.size() > 0) {
                            boolean isSuccess = hashBetService.batchInsert(bets);
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

    public class WorkerThread implements Runnable {

        private GameEntity entity;

        public WorkerThread(GameEntity entity) {
            this.entity = entity;
        }

        @Override
        public void run() {
            long minTimestamp = new Date().getTime() - (3 * 60 * 60 * 1000);
            JSONObject trc20Data = trxApi.getTrc20Record(entity.getAddress(), minTimestamp);
            if (!ObjectUtils.isEmpty(trc20Data)) {

                for (int i = 0; i < trc20Data.size(); i++) {
                    JSONObject jsonObject = trc20Data.getJSONObject(String.valueOf(i));
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


                    BigDecimal b1 = new BigDecimal(value);
                    BigDecimal b2 = new BigDecimal(String.format("%s", Math.pow(10, decimals)));
                    BigDecimal b3 = b1.divide(b2, 2, RoundingMode.DOWN);
                }
            }

        }
    }


}
