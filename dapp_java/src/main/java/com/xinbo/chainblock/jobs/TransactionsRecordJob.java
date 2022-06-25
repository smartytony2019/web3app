package com.xinbo.chainblock.jobs;

import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.entity.RechargeEntity;
import com.xinbo.chainblock.entity.UserEntity;
import com.xinbo.chainblock.entity.WalletEntity;
import com.xinbo.chainblock.entity.terminal.TransactionRecordApiEntity;
import com.xinbo.chainblock.service.RechargeService;
import com.xinbo.chainblock.service.UserService;
import com.xinbo.chainblock.service.WalletService;
import com.xinbo.chainblock.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tony
 * @date 6/25/22 2:59 下午
 * @desc file desc
 */
@Service
public class TransactionsRecordJob {

    @Autowired
    private TrxApi trxApi;

    @Autowired
    private CommonUtils commonUtils;


    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${trx.token-info.symbol}")
    private String tokenSymbol;

    @Value("${trx.token-info.name}")
    private String tokenName;


    private String transactionKey = "trx:transaction:%s";

    @Scheduled(cron = "0/5 * * * * ?")
    public void record() {
        try {
            String account = "TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu";
            List<TransactionRecordApiEntity.Data> transactionsRecord = trxApi.getTransactionsRecord(account);
            if(ObjectUtils.isEmpty(transactionsRecord)) {
                return;
            }

            for (TransactionRecordApiEntity.Data data : transactionsRecord) {
                if(ObjectUtils.isEmpty(data)) {
                    continue;
                }

                if(ObjectUtils.isEmpty(data.getTokenInfo().getSymbol()) || !data.getTokenInfo().getSymbol().equals(tokenSymbol)) {
                    continue;
                }

                if(ObjectUtils.isEmpty(data.getTokenInfo().getName()) ||  !data.getTokenInfo().getName().equals(tokenName)) {
                    continue;
                }


                String key = String.format(transactionKey, data.getTransactionId());
                Boolean isHandle = redisTemplate.hasKey(key);
                if(isHandle) {
                    continue;
                }


                //根据充值地址找到用户钱包
                WalletEntity walletEntity = walletService.findByAddress(data.getFrom());
                if(ObjectUtils.isEmpty(walletEntity) || walletEntity.getId() <= 0) {
                    continue;
                }


                BigDecimal bigDecimal = new BigDecimal(data.getValue());
                BigDecimal value = commonUtils.fromTrc20(bigDecimal);

                UserEntity userEntity = userService.findById(walletEntity.getUid());
                userEntity.setMoney(value.floatValue());
                boolean isSuccess = userService.increment(userEntity);
                if(!isSuccess) {
                    continue;
                }


                RechargeEntity entity = RechargeEntity.builder()
                        .transactionId(data.getTransactionId())
                        .tokenSymbol(data.getTokenInfo().getSymbol())
                        .tokenAddress(data.getTokenInfo().getAddress())
                        .tokenDecimals(data.getTokenInfo().getDecimals())
                        .tokenName(data.getTokenInfo().getName())
                        .blockTimestamp(data.getBlockTimestamp())
                        .fromAddress(data.getFrom())
                        .toAddress(data.getTo())
                        .type(data.getType())
                        .value(value.doubleValue())
                        .build();
                isSuccess = rechargeService.save(entity);
                if(!isSuccess) {
                    continue;
                }

                redisTemplate.opsForValue().set(key, "");
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
