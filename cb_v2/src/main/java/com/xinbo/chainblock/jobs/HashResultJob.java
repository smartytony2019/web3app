package com.xinbo.chainblock.jobs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xinbo.chainblock.consts.RedisConst;
import com.xinbo.chainblock.consts.TrxApiConst;
import com.xinbo.chainblock.entity.hash.HashResultEntity;
import com.xinbo.chainblock.entity.terminal.BaseEntity;
import com.xinbo.chainblock.entity.terminal.HashResultApiEntity;
import com.xinbo.chainblock.service.HashResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author tony
 * @date 6/28/22 5:45 下午
 * @desc file desc
 */
@Slf4j
//@Component
public class HashResultJob {

    @Value("${trx.terminal-url}")
    private String terminalUrl;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private HashResultService hashResultService;


    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "0/5 * * * * ?")
    public void result() {
        try {
            //Step 1: 获取开奖数据
            String url = String.format("%s%s%s", terminalUrl, TrxApiConst.RESULT_OPEN, 5);
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
            String body = forEntity.getBody();
            if (StringUtils.isEmpty(body)) {
                throw new RuntimeException("body is empty");
            }

            BaseEntity<List<HashResultApiEntity>> listBaseEntity = JSON.parseObject(body, new TypeReference<BaseEntity<List<HashResultApiEntity>>>() {
            });
            if (ObjectUtils.isEmpty(listBaseEntity) || listBaseEntity.getCode() != 0) {
                throw new RuntimeException("fetch open data error");
            }


            //Step 2: 过滤重复数据
            List<HashResultApiEntity> records = new ArrayList<>();
            for (HashResultApiEntity entity : listBaseEntity.getData()) {
//                String key = String.format(RedisConst.HASH_RESULT, entity.getGameId(), entity.getNum());
                String key = "";
                Boolean hasKey = redisTemplate.hasKey(key);
                if (hasKey != null && hasKey) {
                    continue;
                }

                records.add(entity);
            }

            if (records.size() <= 0) {
                return;
            }

            //Step 3: 需要开奖数据
            for (HashResultApiEntity entity : records) {
                HashResultEntity hashResultEntity = HashResultEntity.builder()
                        .txID(entity.getTxID())
                        .blockHash(entity.getBlockHash())
                        .blockHeight(entity.getBlockHeight())
                        .openTime(entity.getOpenTime())
                        .openTimestamp(entity.getOpenTimestamp())
                        .network(entity.getNetwork())
                        .build();

                boolean isSuccess = hashResultService.insert(hashResultEntity);
                if(!isSuccess) {
                    continue;
                }

                String key = "";
                redisTemplate.opsForValue().set(key, "", 1L, TimeUnit.DAYS);
            }
        } catch (Exception ex) {
            log.error("result", ex);
        }
    }
}
