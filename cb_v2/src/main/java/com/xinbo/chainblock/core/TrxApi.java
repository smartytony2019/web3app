package com.xinbo.chainblock.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xinbo.chainblock.consts.TrxApiConst;
import com.xinbo.chainblock.entity.terminal.*;
import com.xinbo.chainblock.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author tony
 * @date 6/23/22 5:38 下午
 * @desc file desc
 */
@Service
@Slf4j
public class TrxApi {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CommonUtils commonUtils;

    @Value("${trx.terminal-url}")
    private String terminalUrl;

    @Value("${trx.api-url}")
    private String apiUrl;

    /**
     * 创建帐号
     * @return
     */
    public AccountApiEntity createAccount() {
        AccountApiEntity result = null;
        try {
            String url = String.format("%s%s", terminalUrl, TrxApiConst.CREATE_ACCOUNT);
            String res = restTemplate.postForObject(url, "", String.class);
            BaseEntity<AccountApiEntity> entity = JSON.parseObject(res, new TypeReference<BaseEntity<AccountApiEntity>>() {});
            if(!ObjectUtils.isEmpty(entity) && entity.getCode() == 0 && !ObjectUtils.isEmpty(entity.getData())) {
                result = entity.getData();
            }
        }catch (Exception ex) {
            log.error("TerminalApi createAccount exception", ex);
        }
        return result;
    }


    /**
     * 获取TRX余额
     * @param fromAddress 查询地址
     * @return String
     */
    public String getBalanceOfTrx(String fromAddress) {
        String result = null;
        try {
            String url = String.format("%s%s", terminalUrl, TrxApiConst.GET_BALANCE_TRX);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fromAddress", fromAddress);
            String res = restTemplate.postForObject(url, jsonObject, String.class);
            BaseEntity<String> entity = JSON.parseObject(res, new TypeReference<BaseEntity<String>>() {});
            if(!ObjectUtils.isEmpty(entity) && entity.getCode() == 0) {
                result = entity.getData();
            }
        }catch (Exception ex) {
            log.error("TerminalApi getBalanceOfTrx exception", ex);
        }
        return result;
    }



    /**
     * 获取Trc20余额(USDT)
     * @param fromAddress 查询地址
     * @param privateKey  私钥
     * @return String
     */
    public String getBalanceOfTrc20(String fromAddress, String privateKey) {
        String result = null;
        try {
            String url = String.format("%s%s", terminalUrl, TrxApiConst.GET_BALANCE_TRC20);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fromAddress", fromAddress);
            jsonObject.put("privateKey", privateKey);
            String res = restTemplate.postForObject(url, jsonObject, String.class);
            BaseEntity<String> entity = JSON.parseObject(res, new TypeReference<BaseEntity<String>>() {});
            if(!ObjectUtils.isEmpty(entity) && entity.getCode() == 0) {
                result = entity.getData();
            }
        }catch (Exception ex) {
            log.error("TerminalApi getBalanceOfTrc20 exception", ex);
        }
        return result;
    }



    /**
     * 获取Trc20余额(USDT)
     * @param fromAddress 转帐地址
     * @param privateKey  私钥
     * @param amount      数量
     * @param toAddress   收款地址
     * @return Entity
     */
    public TransactionTrxApiEntity transactionOfTrx(String fromAddress, String privateKey, float amount, String toAddress) {
        TransactionTrxApiEntity result = null;
        try {
            String url = String.format("%s%s", terminalUrl, TrxApiConst.TRANSACTION_TRX);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fromAddress", fromAddress);
            jsonObject.put("amount", commonUtils.toTrx(amount));
            jsonObject.put("toAddress", toAddress);
            jsonObject.put("privateKey", privateKey);
            String res = restTemplate.postForObject(url, jsonObject, String.class);
            BaseEntity<TransactionTrxApiEntity> entity = JSON.parseObject(res, new TypeReference<BaseEntity<TransactionTrxApiEntity>>() {});
            if(!ObjectUtils.isEmpty(entity) && entity.getCode() == 0) {
                result = entity.getData();
            }
        }catch (Exception ex) {
            log.error("TerminalApi transactionOfTrx exception", ex);
        }
        return result;
    }




    /**
     * 获取Trc20余额(USDT)
     * @param contractAddress 合约地址
     * @param fromAddress 转帐地址
     * @param privateKey  私钥
     * @param amount      数量
     * @param toAddress   收款地址
     * @return String
     */
    public TransactionTrxApiEntity transactionOfTrc20(String contractAddress, String fromAddress, String privateKey, double amount, String toAddress) {
        TransactionTrxApiEntity result = null;
        try {
            String url = String.format("%s%s", terminalUrl, TrxApiConst.TRANSACTION_TRC20);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("contractAddress", contractAddress);
            jsonObject.put("fromAddress", fromAddress);
            jsonObject.put("amount", commonUtils.toTrc20(amount));
            jsonObject.put("toAddress", toAddress);
            jsonObject.put("privateKey", privateKey);
            String res = restTemplate.postForObject(url, jsonObject, String.class);
            BaseEntity<TransactionTrxApiEntity> entity = JSON.parseObject(res, new TypeReference<BaseEntity<TransactionTrxApiEntity>>() {});
            if(!ObjectUtils.isEmpty(entity) && entity.getCode() == 0) {
                result = entity.getData();
            }
        }catch (Exception ex) {
            log.error("TerminalApi transactionOfTrc20 exception", ex);
        }
        return result;
    }


    /**
     * 获取交易信息
     * @param txID  转帐id
     * @return entity
     */
    public TransactionInfoApiEntity getTransactionInfo(String txID) {
        TransactionInfoApiEntity result = null;
        try {
            String url = String.format("%s%s", terminalUrl, TrxApiConst.GET_TRANSACTION_INFO);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("txID", txID);
            String res = restTemplate.postForObject(url, jsonObject, String.class);
            BaseEntity<TransactionInfoApiEntity> entity = JSON.parseObject(res, new TypeReference<BaseEntity<TransactionInfoApiEntity>>() {});
            if(!ObjectUtils.isEmpty(entity) && entity.getCode() == 0) {
                result = entity.getData();
            }
        }catch (Exception ex) {
            log.error("TerminalApi getTransactionInfo exception", ex);
        }
        return result;
    }


    /**
     * 获取TRC20转帐记录
     * @param account
     * @return
     */
    public JSONObject getTrc20Record(String account) {
        JSONObject result = null;
        try {
//            long minTimestamp = new Date().getTime() - (60*60*1000*24*30);
            long minTimestamp = 0;
            String url = String.format(TrxApiConst.GET_TRC20_RECORD, apiUrl, account, minTimestamp);
            RestTemplate restTemplate = new RestTemplate();
            String res = restTemplate.getForObject(url, String.class);

            result = JSONObject.parseObject(res);
        }catch (Exception ex) {
            log.error("TerminalApi getTrc20Record exception", ex);
        }
        return result;
    }



    /**
     * 获取TRX转帐记录
     * @param account
     * @return
     */
    public JSONObject getTrxRecord(String account) {
        JSONObject result = null;
        try {
//            long minTimestamp = new Date().getTime() - (60*60*1000*24*30);
            long minTimestamp = 0;
            String url = String.format(TrxApiConst.GET_TRX_RECORD, apiUrl, account, minTimestamp);
            RestTemplate restTemplate = new RestTemplate();
            String res = restTemplate.getForObject(url, String.class);

            result = JSON.parseObject(res);
//            Trc20RecordApiEntity entity = JSON.parseObject(res, new TypeReference<Trc20RecordApiEntity>() {});
//            if(!ObjectUtils.isEmpty(entity) && !ObjectUtils.isEmpty(entity.getData())) {
//                result = entity.getData();
//            }
        }catch (Exception ex) {
            log.error("TerminalApi getTrxRecord exception", ex);
        }
        return result;
    }


    /**
     * 开奖
     * @param sn
     * @param toAddress
     * @return
     */
    public boolean resultOpen(String sn, String toAddress) {
        boolean result = false;
        try {
            String url = String.format("%s%s", terminalUrl, TrxApiConst.RESULT_OPEN);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sn", sn);
            jsonObject.put("toAddress", toAddress);
            String res = restTemplate.postForObject(url, jsonObject, String.class);

            BaseEntity<Boolean> entity = JSON.parseObject(res, new TypeReference<BaseEntity<Boolean>>() {});
            if(!ObjectUtils.isEmpty(entity) && !ObjectUtils.isEmpty(entity.getData())) {
                result = entity.getData();
            }
        }catch (Exception ex) {
            log.error("TerminalApi resultOpen exception", ex);
        }
        return result;
    }
    /**
     * 开奖
     * @param sn
     * @return
     */
    public HashResultApiEntity resultFind(String sn) {
        HashResultApiEntity result = null;
        try {
            String url = String.format("%s%s", terminalUrl, TrxApiConst.RESULT_FIND);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sn", sn);
            String res = restTemplate.postForObject(url, jsonObject, String.class);

            BaseEntity<HashResultApiEntity> entity = JSON.parseObject(res, new TypeReference<BaseEntity<HashResultApiEntity>>() {});
            if(!ObjectUtils.isEmpty(entity) && !ObjectUtils.isEmpty(entity.getData())) {
                result = entity.getData();
            }
        }catch (Exception ex) {
            log.error("TerminalApi resultFind exception", ex);
        }
        return result;
    }

}
