package com.xinbo.chainblock;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xinbo.chainblock.entity.FinanceEntity;
import com.xinbo.chainblock.entity.terminal.BaseEntity;
import com.xinbo.chainblock.entity.terminal.AccountApiEntity;
import com.xinbo.chainblock.entity.terminal.TransactionInfoApiEntity;
import com.xinbo.chainblock.entity.terminal.TransactionApiEntity;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("prod")
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


    @Test
    void getRecordTrx() {
        String json = "{'data':[{'ret':[{'contractRet':'SUCCESS','fee':0}],'signature':['44346cf5f2ada207887feb185dafd1624464181e5d2a12d849e948451cb90f36378e37363a34a4f5cb909d6bb6f8acb81d04d6f5d0c46130ed9e1b42dcac1d9100'],'txID':'a917a8501e1847d9d726509d992e08c9a7df255220a2d79e4393edeca9149804','net_usage':268,'raw_data_hex':'0a02b14922088baa0fb28eadc3e740b8a2dbdea9305a68080112640a2d747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e5472616e73666572436f6e747261637412330a15419b02a3a610d2eeae5efb92a3ac8e7604a55593da1215419abc42dc5374064b3896d3dd382ad2080b8ff84e18c0de810a7097d7d7dea930','net_fee':0,'energy_usage':0,'blockNumber':28946780,'block_timestamp':1660471866000,'energy_fee':0,'energy_usage_total':0,'raw_data':{'contract':[{'parameter':{'value':{'amount':21000000,'owner_address':'419b02a3a610d2eeae5efb92a3ac8e7604a55593da','to_address':'419abc42dc5374064b3896d3dd382ad2080b8ff84e'},'type_url':'type.googleapis.com/protocol.TransferContract'},'type':'TransferContract'}],'ref_block_bytes':'b149','ref_block_hash':'8baa0fb28eadc3e7','expiration':1660471923000,'timestamp':1660471864215},'internal_transactions':[]},{'ret':[{'contractRet':'SUCCESS','fee':1100000}],'signature':['34931eae7759f13293a328a278420f2c9f915812f6d04ada738bea29139cef56bf6f401e3168d89511ddd656abdfc85a9cdf6c059c7ca4914609106c9738677a01'],'txID':'2315d11a3f9c680a6bbdb7a1bd2ffcaa10ec22b7897eedc7e846095df1ad04df','net_usage':0,'raw_data_hex':'0a0257e822083264811e65691ec640c093d3bda9305a68080112640a2d747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e5472616e73666572436f6e747261637412330a1541248525f23c1ec14ef9e96505014abc0a17861a371215419abc42dc5374064b3896d3dd382ad2080b8ff84e1880c2d72f70c6d2cfbda930','net_fee':100000,'energy_usage':0,'blockNumber':28923900,'block_timestamp':1660402530000,'energy_fee':0,'energy_usage_total':0,'raw_data':{'contract':[{'parameter':{'value':{'amount':100000000,'owner_address':'41248525f23c1ec14ef9e96505014abc0a17861a37','to_address':'419abc42dc5374064b3896d3dd382ad2080b8ff84e'},'type_url':'type.googleapis.com/protocol.TransferContract'},'type':'TransferContract'}],'ref_block_bytes':'57e8','ref_block_hash':'3264811e65691ec6','expiration':1660402584000,'timestamp':1660402526534},'internal_transactions':[]}],'success':true,'meta':{'at':1660492570764,'page_size':2}}";
        JSONObject object = JSON.parseObject(json);
        JSONArray data = object.getJSONArray("data");

        List<FinanceEntity> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JSONObject jsonObject = data.getJSONObject(i);
            String txID = jsonObject.getString("txID");
            JSONObject rawData = jsonObject.getObject("raw_data", JSONObject.class);

            long timestamp = rawData.getLong("timestamp");
            JSONObject contract = rawData.getJSONArray("contract").getJSONObject(0);
            JSONObject parameter = contract.getObject("parameter", JSONObject.class);
            JSONObject value = parameter.getObject("value", JSONObject.class);
            BigDecimal amount = value.getBigDecimal("amount");
            String ownerAddress = value.getString("owner_address");
            String toAddress = value.getString("to_address");


            FinanceEntity fe = FinanceEntity.builder()
                    .uid(1)
                    .username("22")
                    .transactionId(txID)
                    .fromAddress(ownerAddress)
                    .toAddress(toAddress)
                    .money(amount.floatValue())
                    .blockTime(DateUtil.date(timestamp))
                    .blockTimestamp(timestamp)
                    .symbol("trx")
                    .type(2)
                    .isAccount(false)
                    .build();
            list.add(fe);
        }

        System.out.println(list);

    }

    /**
     * 创建帐号
     */
    @Test
    void createAccount() {
        String url = String.format("%s%s", T_API, "/trx/createAccount");
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(url, "", String.class);
        BaseEntity<AccountApiEntity> entity = JSON.parseObject(res, new TypeReference<BaseEntity<AccountApiEntity>>() {
        });
        System.out.println(entity);
    }


    @Test
    void getBalanceOfTrx() {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s%s", T_API, "/trx/getBalanceOfTrx");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromAddress", fromAddress);
        String res = restTemplate.postForObject(url, jsonObject, String.class);
        BaseEntity<String> entity = JSON.parseObject(res, new TypeReference<BaseEntity<String>>() {
        });
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
        BaseEntity<String> entity = JSON.parseObject(res, new TypeReference<BaseEntity<String>>() {
        });
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
        BaseEntity<TransactionApiEntity> entity = JSON.parseObject(res, new TypeReference<BaseEntity<TransactionApiEntity>>() {
        });
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
        BaseEntity<TransactionApiEntity> entity = JSON.parseObject(res, new TypeReference<BaseEntity<TransactionApiEntity>>() {
        });
        System.out.println(entity);
    }


    @Test
    void getTransactionInfo() {
        String url = String.format("%s%s", T_API, "/trx/getTransactionInfo");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("txID", "a53831177ae771259a98b51f96ad772a1997d8d2c3e87bd1d7ad8655545dbfcc");

        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(url, jsonObject, String.class);
        BaseEntity<TransactionInfoApiEntity> entity = JSON.parseObject(res, new TypeReference<BaseEntity<TransactionInfoApiEntity>>() {
        });
        System.out.println(entity);
    }


    @Test
    void test() {
        try {
            File jsonFile = ResourceUtils.getFile("classpath:json/zh.json");
            String json = FileUtils.readFileToString(jsonFile, "UTF-8");
            JSONObject jsonArray = JSON.parseObject(json);
            System.out.println(jsonArray);
        } catch (Exception ex) {

        }
    }

}
