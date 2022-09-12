package com.xinbo.chainblock.jobs;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinbo.chainblock.bo.AccountApiBo;
import com.xinbo.chainblock.consts.ActivityConst;
import com.xinbo.chainblock.consts.GlobalConst;
import com.xinbo.chainblock.consts.RedisConst;
import com.xinbo.chainblock.core.TrxApi;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.activity.ActivityRecordEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleItemEntity;
import com.xinbo.chainblock.enums.MemberFlowItemEnum;
import com.xinbo.chainblock.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
public class MemberJob {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private WalletService walletService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityRuleService activityRuleService;
    @Autowired
    private ActivityRecordService activityRecordService;


    @Autowired
    private CommonService commonService;

    @Autowired
    private TrxApi trxApi;

    @Value("${scheduled.enable.register}")
    private boolean isRegister;

    @Value("${scheduled.enable.transfer}")
    private boolean isTransfer;


    /**
     * 处理注册
     */
    @Scheduled(cron = "0/3 * * * * ?")
    public void handleRegister() {
        try {
            if (!isRegister) {
                return;
            }

            String json = redisTemplate.opsForList().rightPop(RedisConst.MEMBER_REGISTER);
            if (StringUtils.isEmpty(json)) {
                return;
            }
            MemberEntity entity = JSON.parseObject(json, MemberEntity.class);
            if (ObjectUtils.isEmpty(entity) || entity.getId() <= 0) {
                return;
            }

            // ******************************* - 查看是否有注册赠送彩金 -  ************************************************
            ActivityEntity activityEntity = activityService.findByType(ActivityConst.ACTIVITY_TYPE_REGISTER);
            if (!ObjectUtils.isEmpty(activityEntity)) {
                ActivityRuleEntity ruleEntity = activityRuleService.findBySn(activityEntity.getSn());
                if (!ObjectUtils.isEmpty(ruleEntity)) {
                    Float money = ruleEntity.getMoney();

                    ActivityRecordEntity re = ActivityRecordEntity.builder()
                            .activityId(activityEntity.getId())
                            .activityTitle(activityEntity.getTitle())
                            .uid(entity.getId())
                            .username(entity.getUsername())
                            .money(money)
                            .symbol(ruleEntity.getSymbol())
                            .status(ActivityConst.ACTIVITY_TYPE_REGISTER)
                            .createTime(new Date())
                            .remark("member register")
                            .build();

                    // 会员表
                    MemberEntity member = MemberEntity.builder()
                            .id(entity.getId())
                            .money(money)
                            .version(entity.getVersion())
                            .build();

                    // 会员注水表
                    float beforeMoney = entity.getMoney();
                    float afterMoney = entity.getMoney() + money;
                    float flowMoney = money;
                    MemberFlowEntity memberFlow = MemberFlowEntity.builder()
                            .sn(activityEntity.getSn())
                            .uid(entity.getId())
                            .username(entity.getUsername())
                            .beforeMoney(beforeMoney)
                            .afterMoney(afterMoney)
                            .flowMoney(flowMoney)
                            .item(MemberFlowItemEnum.ACTIVITY_RECEIVE.getName())
                            .itemCode(MemberFlowItemEnum.ACTIVITY_RECEIVE.getCode())
                            .itemZh(MemberFlowItemEnum.ACTIVITY_RECEIVE.getNameZh())
                            .createTime(new Date())
                            .build();

                    // 统计表
                    StatisticsEntity statistics = StatisticsEntity.builder()
                            .date(DateUtil.format(new Date(), GlobalConst.DATE_YMD))
                            .uid(entity.getId())
                            .username(entity.getUsername())
                            .activityAmount(money)
                            .updateTime(new Date())
                            .build();
                    boolean isSuccess = activityRecordService.submit(Collections.singletonList(re), member, memberFlow, statistics);
                    if (!isSuccess) {
                        throw new RuntimeException("注册赠送彩金失败");
                    }
                }
            }


            // *********************************** - 创建数字钱包 -  ****************************************************
            // Step 1: 创建数字钱包
            AccountApiBo account = trxApi.createAccount();
            if (ObjectUtils.isEmpty(account) || StringUtils.isEmpty(account.getPrivateKey())) {
                // @todo
                throw new RuntimeException("生成数字钱包失败");
            }

            WalletEntity walletEntity = WalletEntity.builder()
                    .uid(entity.getId())
                    .username(entity.getUsername())
                    .type(1)
                    .privateKey(account.getPrivateKey())
                    .publicKey(account.getPublicKey())
                    .addressBase58(account.getAddress().getBase58())
                    .addressHex(account.getAddress().getHex())
                    .isMain(false)
                    .build();
            boolean isSuccess = walletService.insert(walletEntity);
            if (!isSuccess) {
                throw new RuntimeException("保存数字钱包失败");
            }


            // *********************************** - 更新代理层级 -  ****************************************************
            // Step 1: 获取代理层级表
            int page = 1;
            int size = 50;
            List<AgentEntity> list = new ArrayList<>();
            while (true) {
                int skip = (page - 1) * size;
                List<AgentEntity> res = agentService.findAll(skip, size);
                if (CollectionUtils.isEmpty(res) || res.size() <= 0) {
                    break;
                }

                list = Stream.of(list, res).flatMap(Collection::stream).collect(Collectors.toList());
                if (res.size() != size) {
                    break;
                }
                page += 1;
            }

            AgentEntity firstEntity = list.stream().filter(f -> f.getLevel() == 0).findFirst().orElse(null);
            if (ObjectUtils.isEmpty(firstEntity) || firstEntity.getId() <= 0) {
                return;
            }


            //递归获取数据
            int i = 1;
            while (true) {
                int finalI = i;
                List<AgentEntity> agentList = list.stream().filter(f -> f.getLevel() == finalI).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(agentList) || agentList.size() <= 0) {
                    break;
                }
                for (AgentEntity e : agentList) {
                    List<AgentEntity> loop = this.loop(list, e.getUid());

                    //大于1说明下面有用户
                    if (CollectionUtils.isEmpty(loop) || loop.size() <= 1) {
                        continue;
                    }

                    //排除自己
                    List<Integer> collect = loop.stream().filter(f -> !f.getUid().equals(e.getUid())).sorted(Comparator.comparing(AgentEntity::getUid)).map(AgentEntity::getUid).collect(Collectors.toList());


                    String collect1 = collect.stream().map(Objects::toString).collect(Collectors.joining(","));
                    isSuccess = agentService.setChild(e.getId(), collect1);
                    if (isSuccess) {
                        log.info("update child success");
                    } else {
                        log.error("update child fail");
                    }
                }
                i += 1;
            }
        } catch (Exception ex) {
            log.error("MemberJob", ex);
        }
    }


    @Scheduled(cron = "0/3 * * * * ?")
    public void handleTransfer() {
        try {
            if (!isTransfer) {
                return;
            }

            String json = redisTemplate.opsForList().rightPop(RedisConst.MEMBER_TRANSFER);
            if (StringUtils.isEmpty(json)) {
                return;
            }

            JSONObject object = JSON.parseObject(json);
            MemberEntity member = object.getObject("member", MemberEntity.class);
            MemberFlowEntity flow = object.getObject("flow", MemberFlowEntity.class);

            boolean isSuccess = commonService.transfer(member, flow);
            if (!isSuccess) {
                redisTemplate.opsForList().leftPush(RedisConst.MEMBER_TRANSFER, json);
            }
        } catch (Exception ex) {
            log.error("handleTransfer", ex);
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
