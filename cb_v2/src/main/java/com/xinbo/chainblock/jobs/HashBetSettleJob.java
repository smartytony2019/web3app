package com.xinbo.chainblock.jobs;

import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.core.algorithm.HashAlgorithm;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.terminal.HashResultApiEntity;
import com.xinbo.chainblock.service.HashBetService;
import com.xinbo.chainblock.service.HashResultService;
import com.xinbo.chainblock.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author tony
 * @date 7/1/22 5:08 下午
 * @desc 哈希注单结算任务
 */
@Slf4j
//@Component
public class HashBetSettleJob {

    @Autowired
    private HashResultService hashResultService;

    @Autowired
    private HashBetService hashBetService;

    @Autowired
    private TrxApi trxApi;

    @Autowired
    private MemberService memberService;

    @Autowired
    private HashAlgorithm hashAlgorithm;


    @Autowired
    private StringRedisTemplate redisTemplate;


    @Scheduled(cron = "0/5 * * * * ?")
    public void settle() {
        try {
            System.out.println("@Scheduled start---> " + new Date());

            // Step 1: 未结算数据
            HashBetEntity hashBetEntity = hashBetService.unsettle();
            if(ObjectUtils.isEmpty(hashBetEntity) || hashBetEntity.getId() <= 0) {
                System.out.println("not find unsettle data");
                return;
            }

            // Step 2: 生成开奖
            HashResultApiEntity resultApiEntity = trxApi.resultFind(hashBetEntity.getSn());
            if(ObjectUtils.isEmpty(resultApiEntity) || StringUtils.isEmpty(resultApiEntity.getBlockHash())) {
                return;
            }

            System.out.println("success");

            // Step 3: 结算

            // Step 4: 派奖

            // Step 5: 统计


            System.out.println("@Scheduled end-----> " + new Date());
        } catch (RuntimeException ex) {
            log.error("settle: ", ex);
        }
    }


}
