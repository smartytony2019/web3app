package com.xinbo.chainblock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xinbo.chainblock.entity.terminal.ResponseEntity;
import com.xinbo.chainblock.entity.terminal.AccountApiEntity;
import com.xinbo.chainblock.entity.terminal.TransactionInfoApiEntity;
import com.xinbo.chainblock.entity.terminal.TransactionRecordApiEntity;
import com.xinbo.chainblock.entity.terminal.TransactionTrxApiEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.TimeZone;

//@RunWith(SpringRunner.class)
@SpringBootTest
class ChainblockApplicationTests {

    private static String URL = "https://nile.trongrid.io/wallet/";
    private static String API = "https://nile.trongrid.io/v1";

    private static String account = "TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu";


    private static String T_API = "http://localhost:3333";
    private static String TRX_API = "";


    private String contractAddress = "TZ5YTid3VphzLpgwSks24KFuyL7wgxuEBR";
    private String fromAddress = "TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu";
    private String toAddress = "TEuyVZdSXR8PaFmB8wX1LiZ3getos5Yuwe";
    private String privateKey = "f58c1b3a3db8c4024d34427543dfcd6482b0bc7a0619a7d344b216a3be4f7703";

    /**
     * 获取转帐信息
     */
    @Test
    void getRecordTrc20() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        long minTimestamp = new Date().getTime() - (60*60*1000*24*30);
        //&min_timestamp=%s
        String url = String.format("%s/accounts/%s/transactions/trc20?only_confirmed=true&only_to=true&limit=200", API, account, minTimestamp);
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(url, String.class);

        TransactionRecordApiEntity entity = JSON.parseObject(res, new TypeReference<TransactionRecordApiEntity>() {});
        if(!ObjectUtils.isEmpty(entity) && !ObjectUtils.isEmpty(entity.getData()) && entity.getData().size()>0) {
            for (TransactionRecordApiEntity.Data d: entity.getData()) {
                System.out.println(d);
            }
        }
        System.out.println(entity);
    }

    /**
     * 创建帐号
     */
    @Test
    void createAccount() {
        String url = String.format("%s%s", T_API, "/trx/createAccount");
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(url, "", String.class);
        ResponseEntity<AccountApiEntity> entity = JSON.parseObject(res, new TypeReference<ResponseEntity<AccountApiEntity>>() {});
        System.out.println(entity);
    }


    @Test
    void getBalanceOfTrx() {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s%s", T_API, "/trx/getBalanceOfTrx");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromAddress", fromAddress);
        String res = restTemplate.postForObject(url, jsonObject, String.class);
        ResponseEntity<String> entity = JSON.parseObject(res, new TypeReference<ResponseEntity<String>>() {});
        System.out.println(entity);
    }


    @Test
    void getBalanceOfTrc20() {
        String url = String.format("%s%s", T_API, "/trx/getBalanceOfTrc20");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromAddress", fromAddress);
        jsonObject.put("privateKey", privateKey);


        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(url, jsonObject, String.class);
        ResponseEntity<String> entity = JSON.parseObject(res, new TypeReference<ResponseEntity<String>>() {});
        System.out.println(entity);
    }


    @Test
    void transactionOfTrx() {
        String url = String.format("%s%s", T_API, "/trx/transactionOfTrx");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromAddress", fromAddress);
        jsonObject.put("amount", 1000000);
        jsonObject.put("toAddress", toAddress);
        jsonObject.put("privateKey", privateKey);

        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(url, jsonObject, String.class);
        ResponseEntity<TransactionTrxApiEntity> entity = JSON.parseObject(res, new TypeReference<ResponseEntity<TransactionTrxApiEntity>>() {});
        System.out.println(entity);
    }


    @Test
    void transactionOfTrc20() {
        String url = String.format("%s%s", T_API, "/trx/transactionOfTrc20");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("contractAddress", contractAddress);
        jsonObject.put("fromAddress", fromAddress);
        jsonObject.put("amount", 1000000000000000000L);
        jsonObject.put("toAddress", toAddress);
        jsonObject.put("privateKey", privateKey);

        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(url, jsonObject, String.class);
        ResponseEntity<TransactionTrxApiEntity> entity = JSON.parseObject(res, new TypeReference<ResponseEntity<TransactionTrxApiEntity>>() {});
        System.out.println(entity);
    }


    @Test
    void getTransactionInfo() {
        String url = String.format("%s%s", T_API, "/trx/getTransactionInfo");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("txID", "a53831177ae771259a98b51f96ad772a1997d8d2c3e87bd1d7ad8655545dbfcc");

        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(url, jsonObject, String.class);
        ResponseEntity<TransactionInfoApiEntity> entity = JSON.parseObject(res, new TypeReference<ResponseEntity<TransactionInfoApiEntity>>() {});
        System.out.println(entity);
    }

}
