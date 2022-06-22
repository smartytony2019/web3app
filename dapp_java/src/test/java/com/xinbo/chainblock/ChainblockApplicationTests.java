package com.xinbo.chainblock;

import com.alibaba.fastjson.JSONObject;
import com.xinbo.chainblock.utils.HttpUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ChainblockApplicationTests {

    private static String URL = "https://nile.trongrid.io/wallet/";
    private static String API = "https://nile.trongrid.io/v1";

    private static String account = "TDJJqGNpkZpSioBegZM8yyq1K7YnZA17nu";

    @Test
    void contextLoads() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", "29343be4b368bf47918d29957e3ed38c5204bb0bbcbb13a504ef3fb9092dfe2a");
        String json = jsonObject.toJSONString();
        String body = HttpUtil.post(URL + "gettransactioninfobyid", json);
        System.out.println(body);

    }


    @Test
    void getRecordTrc20() {
        String url = String.format("%s/accounts/%s/transactions/trc20", API, account);
        String body = HttpUtil.get(url);
        System.out.println(body);
    }


}
