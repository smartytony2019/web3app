package com.xinbo.chainblock.jobs;

import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.consts.GlobalConst;
import com.xinbo.chainblock.entity.AgentCommissionEntity;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.AgentRebateEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.service.AgentCommissionService;
import com.xinbo.chainblock.service.AgentRebateService;
import com.xinbo.chainblock.service.AgentService;
import com.xinbo.chainblock.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * @date 7/5/22 5:55 下午
 * @desc 计算代理佣金
 */
@Slf4j
@Component
public class AgentCommissionJob {

    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentRebateService agentRebateService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private AgentCommissionService agentCommissionService;

    @Value("${scheduled.enable.agent.commission}")
    private boolean isAgentCommission;

    private static final int UNIT = 10000;
    private static final String REGEX = ",";

    /**
     * 计算代理佣金
     * <p>
     * 10分钟执行一下 0 0/10 * * * ?
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void handle() {
        if (!isAgentCommission) {
            return;
        }


//        String date = "20220827";
        String date = DateUtil.offsetMinute(DateUtil.date(), 30).toString(GlobalConst.DATE_YMD);

        List<StatisticsEntity> statistics = statisticsService.findByDate(date);
        if (CollectionUtils.isEmpty(statistics)) {
            return;
        }

        List<AgentRebateEntity> rebates = agentRebateService.findAll();

        //Step 1: 获取代理层级表
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

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        Map<Integer, Float> map = new HashMap<>();
        for (AgentEntity entity : list) {
            if (entity.getLevel() == 0) {
                continue;
            }

            if (StringUtils.isEmpty(entity.getChild())) {
                // 自己
                StatisticsEntity statisticsEntity = statistics.stream().filter(f -> f.getUid().equals(entity.getUid())).findFirst().orElse(null);
                if (ObjectUtils.isEmpty(statisticsEntity)) {
                    continue;
                }
                map.put(entity.getUid(), ObjectUtils.isEmpty(statisticsEntity.getBetAmount()) ? 0 : statisticsEntity.getBetAmount());
            } else {
                //下级
                List<Integer> childList = Arrays.stream(entity.getChild().split(REGEX)).map(Integer::parseInt).collect(Collectors.toList());
                childList.add(entity.getUid());
                List<StatisticsEntity> childStatistics = statisticsService.findByUidStr(date, childList);

                double sum = childStatistics.stream().mapToDouble(m -> ObjectUtils.isEmpty(m.getBetAmount()) ? 0 : m.getBetAmount()).sum();
                map.put(entity.getUid(), (float) sum);
            }
        }

        Map<Integer, List<AgentCommissionEntity>> pair = new HashMap<>();

        //递归获取数据
        int i = 1;
        while (true) {
            int finalI = i;
            List<AgentEntity> parentAgentList = list.stream().filter(f -> f.getLevel() == finalI).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(parentAgentList) || parentAgentList.size() <= 0) {
                break;
            }
            //上级
            for (AgentEntity parent : parentAgentList) {
                if (!map.containsKey(parent.getUid())) {
                    continue;
                }

                //下级
                List<AgentEntity> childAgentList = list.stream().filter(f -> f.getPUid().equals(parent.getUid())).collect(Collectors.toList());
                List<AgentCommissionEntity> betMoneyList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(childAgentList)) {
                    // 下级佣金
                    for (AgentEntity child : childAgentList) {
                        if (!map.containsKey(child.getUid())) {
                            continue;
                        }
                        float betMoney = map.get(child.getUid());
                        AgentRebateEntity rebateEntity = this.getRebate(rebates, betMoney);
                        if (ObjectUtils.isEmpty(rebateEntity)) {
                            continue;
                        }
                        Integer curRebate = rebateEntity.getRebate();

                        AgentCommissionEntity ce = AgentCommissionEntity.builder()
                                .uid(child.getUid())
                                .username(child.getUsername())
                                .totalCommission(0F)
                                .selfCommission(0F)
                                .directPerformance(0F)
                                .totalPerformance(betMoney)
                                .teamPerformance(0F)
                                .selfPerformance(0F)
                                .rebate(curRebate)
                                .build();
                        betMoneyList.add(ce);
                    }
                }

                AgentCommissionEntity entity = AgentCommissionEntity.builder()
                        .uid(parent.getUid())
                        .username(parent.getUsername())
                        .totalCommission(0F)
                        .selfCommission(0F)
                        .directPerformance(0F)
//                        .teamPerformance(map.get(parent.getUid()))
                        .build();

                //直属
                List<Integer> directList = betMoneyList.stream().map(AgentCommissionEntity::getUid).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(directList)) {
                    List<StatisticsEntity> directStatistics = statistics.stream().filter(f -> directList.contains(f.getUid())).collect(Collectors.toList());

                    double sum = directStatistics.stream().mapToDouble(StatisticsEntity::getBetAmount).sum();
                    entity.setDirectPerformance((float) sum);
                }


                //自营
                StatisticsEntity parentStatistics = statistics.stream().filter(f -> f.getUid().equals(parent.getUid())).findFirst().orElse(null);
                float betAmount = 0F;
                if (!ObjectUtils.isEmpty(parentStatistics) && !ObjectUtils.isEmpty(parentStatistics.getBetAmount())) {
                    betAmount = parentStatistics.getBetAmount();
                }

                //总业绩
                float totalPerformance = 0F;
                if (directList.size() == 0) {
                    totalPerformance = map.get(parent.getUid());
                } else {
                    totalPerformance = map.get(parent.getUid()) + betAmount;
                }

                // 团队业绩
                float teamPerformance = totalPerformance - betAmount;


//                float totalPerformance = map.get(parent.getUid()) + parentStatistics.getBetAmount();
                AgentRebateEntity rebateEntity = this.getRebate(rebates, totalPerformance);
                if (ObjectUtils.isEmpty(rebateEntity)) {
                    continue;
                }
                Integer curRebate = rebateEntity.getRebate();

                entity.setTotalPerformance(totalPerformance);
                entity.setTeamPerformance(teamPerformance);
                entity.setSelfPerformance(betAmount);
                entity.setSelfCommission(betAmount * curRebate / UNIT);
                entity.setSubPerformance(0F);
                entity.setRebate(curRebate);
                betMoneyList.add(entity);

                pair.put(parent.getUid(), betMoneyList);
            }
            i += 1;
        }

        //计算佣金
        List<AgentCommissionEntity> agentCommissionEntityList = new ArrayList<>();
        for (Map.Entry<Integer, List<AgentCommissionEntity>> entry : pair.entrySet()) {
            Integer key = entry.getKey();
            List<AgentCommissionEntity> value = entry.getValue();
            AgentCommissionEntity self = value.stream().filter(f -> f.getUid().equals(key)).findFirst().orElse(null);
            if (ObjectUtils.isEmpty(self)) {
                continue;
            }
            for (AgentCommissionEntity en : value) {
                if (en.getUid().equals(key)) {
                    float team = self.getRebate() * self.getTotalPerformance() / UNIT;
                    self.setTotalCommission(self.getTotalCommission() + team);
                    continue;
                }

                int rebate = self.getRebate() - en.getRebate();
                float cha = rebate * en.getTotalPerformance() / UNIT;
                self.setTotalCommission(self.getTotalCommission() + cha);
            }
            self.setDate(date);
            self.setCreateTime(DateUtil.date());
            self.setCreateTimestamp(DateUtil.current());
            self.setSubPerformance(self.getTeamPerformance() - self.getDirectPerformance());
            //人数
            AgentEntity agentEntity = list.stream().filter(f -> f.getUid().equals(key)).findFirst().orElse(null);
            if(!ObjectUtils.isEmpty(agentEntity)) {
                String child = agentEntity.getChild();
                int count = StringUtils.isEmpty(child) ? 0 : child.split(REGEX).length;
                self.setTeamCount(count);
            }
            List<AgentEntity> collect = list.stream().filter(f -> f.getPUid().equals(key)).collect(Collectors.toList());
            self.setDirectCount(collect.size());

            // 判断没有直属佣金
            if (self.getDirectPerformance() <= 0 && self.getSelfPerformance() <= 0) {
                continue;
            }


            agentCommissionEntityList.add(self);
        }

        if (agentCommissionEntityList.size() <= 0) {
            return;
        }

        boolean isSuccess = agentCommissionService.insertOrUpdate(agentCommissionEntityList);
//        System.out.println(isSuccess);
    }


    private AgentRebateEntity getRebate(List<AgentRebateEntity> list, double value) {
        for (AgentRebateEntity entity : list) {
            if (value > entity.getMin() && value <= entity.getMax()) {
                return entity;
            }
        }
        return null;
    }


}
