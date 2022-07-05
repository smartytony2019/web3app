package com.xinbo.chainblock.jobs;

import com.xinbo.chainblock.consts.RedisConst;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.service.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tony
 * @date 7/4/22 6:39 下午
 * @desc 代理调整结构
 */
@Slf4j
//@Component
public class AgentAdjustStructureJob {


    @Autowired
    private AgentService agentService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 结算佣金
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void settleCommission() {
//        Object o = redisTemplate.opsForValue().get(RedisConst.AGENT_FIXED);
//        if(ObjectUtils.isEmpty(o)) {
//            return;
//        }

        //Step 1: 获取代理层级表
        int page = 1;
        int size = 2;
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
            for (AgentEntity entity : agentList) {
                List<AgentEntity> loop = this.loop(list, entity.getUid());

                //大于1说明下面有用户
                if(CollectionUtils.isEmpty(loop) || loop.size() <= 1) {
                    continue;
                }

                //排除自己
                List<Integer> collect = loop.stream().filter(f -> !f.getUid().equals(entity.getUid())).sorted(Comparator.comparing(AgentEntity::getUid)).map(AgentEntity::getUid).collect(Collectors.toList());


                String collect1 = collect.stream().map(Objects::toString).collect(Collectors.joining(","));
                boolean isSuccess = agentService.setChild(entity.getId(), collect1);
                if(isSuccess) {
                    log.info("update child success");
                } else {
                    log.error("update child fail");
                }
            }
            i += 1;
        }

        redisTemplate.delete(RedisConst.AGENT_FIXED);
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
