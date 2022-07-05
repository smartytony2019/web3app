package com.xinbo.chainblock.jobs;

import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.AgentRebateEntity;
import com.xinbo.chainblock.mapper.AgentRebateMapper;
import com.xinbo.chainblock.service.AgentRebateService;
import com.xinbo.chainblock.service.AgentService;
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


    @Scheduled(cron = "0/5 * * * * ?")
    public void handle() {


        List<AgentRebateEntity> rebates = agentRebateService.findAll();



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

        if(CollectionUtils.isEmpty(list)) {
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

                String child = entity.getChild();
                if(StringUtils.isEmpty(child)) {
                    continue;
                }
                List<Integer> childList = Arrays.stream(child.split(",")).map(Integer::parseInt).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                for (int childUid : childList) {
                    System.out.println(childUid);
                }
                System.out.println("----------------");
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
