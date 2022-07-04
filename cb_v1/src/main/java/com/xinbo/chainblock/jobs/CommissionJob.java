package com.xinbo.chainblock.jobs;

import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tony
 * @date 7/4/22 6:39 下午
 * @desc 代理佣金任务
 */

@Component
public class CommissionJob {


    @Autowired
    private AgentService agentService;


    /**
     * 结算佣金
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void settleCommission() {

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
                System.out.println(entity);
                System.out.println(loop);
                System.out.println("----------------------------");
            }
            i += 1;
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
