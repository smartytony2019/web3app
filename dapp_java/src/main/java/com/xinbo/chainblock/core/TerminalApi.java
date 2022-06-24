package com.xinbo.chainblock.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xinbo.chainblock.entity.terminal.ResponseEntity;
import com.xinbo.chainblock.entity.terminal.AccountApiEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author tony
 * @date 6/23/22 5:38 下午
 * @desc file desc
 */
@Service
public class TerminalApi {

    @Value("{terminal.url}")
    private String terminalUrl;

    public void createAccount() {
        String url = String.format("%s%s", terminalUrl, "/trx/createAccount");
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(url, "", String.class);
        ResponseEntity<AccountApiEntity> entity = JSON.parseObject(res, new TypeReference<ResponseEntity<AccountApiEntity>>() {});
        System.out.println(entity);
    }

}
