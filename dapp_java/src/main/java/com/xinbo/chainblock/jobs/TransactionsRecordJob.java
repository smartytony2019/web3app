package com.xinbo.chainblock.jobs;

import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.entity.terminal.TransactionRecordApiEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${trx.token-info.symbol}")
    private String tokenSymbol;

    @Value("${trx.token-info.name}")
    private String tokenName;


    private String transaction_key = "trx:transaction:%s";

    @Scheduled(cron = "0/3 * * * * ?")
    public void record() {

        String account = "TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu";
        List<TransactionRecordApiEntity.Data> transactionsRecord = trxApi.getTransactionsRecord(account);
        if(ObjectUtils.isEmpty(transactionsRecord)) {
            return;
        }

        for (TransactionRecordApiEntity.Data data : transactionsRecord) {
            if(ObjectUtils.isEmpty(data)) {
                continue;
            }

            if(ObjectUtils.isEmpty(data.getToken_info().getSymbol()) || !data.getToken_info().getSymbol().equals(tokenSymbol)) {
                continue;
            }

            if(ObjectUtils.isEmpty(data.getToken_info().getName()) ||  !data.getToken_info().getName().equals(tokenName)) {
                continue;
            }


            String key = String.format(transaction_key, data.getTransaction_id());
            Boolean isHandle = redisTemplate.hasKey(key);
            if(isHandle) {
                continue;
            }




            System.out.println(data);
        }




    }

}
