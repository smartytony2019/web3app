package com.xinbo.chainblock.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author 熊二
 * @date 6/23/22 4:48 下午
 * @desc file desc
 */
@Service
public class LotteryNiuNiuJob {

    @Scheduled(cron = "30 * * * * ?")
    public void NuiNiu() {
        System.out.println("ok");
    }


}
