package com.xinbo.chainblock.jobs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xinbo.chainblock.consts.RedisConst;
import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.entity.SystemFlowEntity;
import com.xinbo.chainblock.entity.WalletEntity;
import com.xinbo.chainblock.service.SystemFlowService;
import com.xinbo.chainblock.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author tony
 * @date 8/30/22 6:11 下午
 * @desc file desc
 */
@Slf4j
@Component
public class SystemFLowJob {


    @Autowired
    private TrxApi trxApi;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SystemFlowService systemFlowService;

    @Autowired
    private WalletService walletService;


    @Value("${scheduled.enable.system.flow}")
    private boolean isSystemFlow;

    @Value("${trx.token-info.contract-address}")
    private String contractAddress;

    /**
     * 处理队列
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void handleSystemFlowQueue() {

        try {
            if (!isSystemFlow) {
                return;
            }
            String json = redisTemplate.opsForSet().pop(RedisConst.SYSTEM_FLOW);
            SystemFlowEntity flowEntity = JSON.parseObject(json, new TypeReference<SystemFlowEntity>() {});
            if(ObjectUtils.isEmpty(flowEntity) || StringUtils.isEmpty(flowEntity.getSn())) {
                return;
            }

            WalletEntity main = walletService.findMain();

            String balanceOfTrc20 = trxApi.getBalanceOfTrc20(contractAddress, main.getAddressBase58(), main.getPrivateKey());
            if(StringUtils.isEmpty(balanceOfTrc20)) {
                return;
            }

            float balance = Float.parseFloat(balanceOfTrc20);

            flowEntity.setBeforeMoney(balance);
            flowEntity.setAfterMoney(balance+flowEntity.getFlowMoney());
            systemFlowService.insert(flowEntity);
        } catch (Exception ex) {
            log.error("handleSystemFlowQueue exception", ex);
        }

    }

}
