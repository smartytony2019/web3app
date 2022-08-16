package com.xinbo.chainblock.jobs;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.enums.ItemEnum;
import com.xinbo.chainblock.service.AccountService;
import com.xinbo.chainblock.service.MemberService;
import com.xinbo.chainblock.service.TransferService;
import com.xinbo.chainblock.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tony
 * @date 8/13/22 4:45 下午
 * @desc file desc
 */
@Slf4j
@Component
public class TransferJob {


    @Autowired
    private WalletService walletService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TrxApi trxApi;

    /**
     * 处理转换
     */
    @Scheduled(cron = "0/3 * * * * ?")
    public void handleTransfer() {
        try {
            long expired = DateUtil.currentSeconds();
            List<TransferEntity> unconfirmed = transferService.findUnconfirmed(expired);
            if (CollectionUtils.isEmpty(unconfirmed)) {
                return;
            }

            WalletEntity main = walletService.findMain();
            if (ObjectUtils.isEmpty(main)) {
                return;
            }


            JSONObject record = trxApi.getTrc20Record(main.getAddressBase58());
            if (ObjectUtils.isEmpty(record)) {
                return;
            }


            List<String> transactionIds = new ArrayList<>();
            JSONArray data = record.getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                if (ObjectUtils.isEmpty(jsonObject)) {
                    continue;
                }

                String transactionId = jsonObject.getString("transaction_id");
                if (StringUtils.isEmpty(transactionId)) {
                    continue;
                }

                transactionIds.add(transactionId);
            }


            for (TransferEntity e : unconfirmed) {
                if (!transactionIds.contains(e.getTransactionId())) {
                    continue;
                }

                MemberEntity memberEntity = memberService.findById(e.getUid());
                if (ObjectUtils.isEmpty(memberEntity)) {
                    continue;
                }

                // Step 1: 交易金额变更
                MemberEntity member = MemberEntity.builder()
                        .id(memberEntity.getId())
                        .money(e.getMoney())
                        .version(memberEntity.getVersion())
                        .build();


                float money = e.getType() == 1 ? e.getMoney() : e.getMoney() * -1;
                ItemEnum itemEnum = e.getType() == 1 ? ItemEnum.TRANSFER_FUNDING2TRADING : ItemEnum.TRANSFER_TRADING2FUNDING;
                // Step 2: 帐变
                MemberFlowEntity flow = MemberFlowEntity.builder()
                        .sn(e.getTransactionId())
                        .uid(memberEntity.getId())
                        .username(memberEntity.getUsername())
                        .beforeMoney(memberEntity.getMoney())
                        .flowMoney(money)
                        .afterMoney(memberEntity.getMoney() + money)
                        .item(itemEnum.getCode())
                        .itemZh(itemEnum.getMsg())
                        .createTime(new Date())
                        .build();

                e.setStatus(1);
                accountService.transfer(e, member, flow);
            }
        } catch (Exception ex) {
            log.error("MemberJob", ex);
        }
    }


    private List<AgentEntity> loop(List<AgentEntity> list, int uid) {
        //下级
        List<AgentEntity> childAgent = list.stream().filter(f -> f.getPUid() == uid).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(childAgent) || childAgent.size() <= 0) {
            //没有下级，返回当前节点
            return list.stream().filter(f -> f.getUid() == uid).collect(Collectors.toList());
        }
        List<AgentEntity> result = new ArrayList<>();
        for (AgentEntity child : childAgent) {
            List<AgentEntity> loop = this.loop(list, child.getUid());
            if (CollectionUtils.isEmpty(loop) || loop.size() <= 0) {
                return null;
            }

            result = Stream.of(result, loop).flatMap(Collection::stream).collect(Collectors.toList());
        }
        //递归完成，把上级节点合并进结果集
        result.addAll(list.stream().filter(f -> f.getUid() == uid).collect(Collectors.toList()));
        return result;
    }


}
