package com.xinbo.chainblock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.core.algorithm.AlgorithmResult;
import com.xinbo.chainblock.core.algorithm.HashAlgorithm;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.terminal.BaseEntity;
import com.xinbo.chainblock.entity.terminal.HashResultApiEntity;
import com.xinbo.chainblock.entity.terminal.TransactionTrxApiEntity;
import com.xinbo.chainblock.service.HashBetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author tony
 * @date 8/10/22 1:37 下午
 * @desc file desc
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("company")
@SpringBootTest
public class BetTest {


    @Autowired
    private TrxApi trxApi;

    @Autowired
    private HashBetService hashBetService;

    @Autowired
    private HashAlgorithm hashAlgorithm;

    private static String T_API = "http://localhost:3333";
    private String contractAddress = "TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR";
    private String fromAddress = "TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu";
    private String toAddress = "TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe";
    private String privateKey = "f58c1b3a3db8c4024d34427543dfcd6482b0bc7a0619a7d344b216a3be4f7703";

    @Test
    public void test01() {

        // Step 1: 未结算数据
        HashBetEntity bet = hashBetService.unsettle();
        if(ObjectUtils.isEmpty(bet) || bet.getId() <= 0) {
            System.out.println("not find unsettle data");
            return;
        }

        // Step 2: 生成开奖
        HashResultApiEntity result = trxApi.resultFind(bet.getSn());
        if(ObjectUtils.isEmpty(result) || StringUtils.isEmpty(result.getBlockHash())) {
            return;
        }

        // Step 3: 结算
        AlgorithmResult algorithmResult = hashAlgorithm.settle(result, bet);



        // Step 4: 派奖

        // Step 5: 统计
    }

}
