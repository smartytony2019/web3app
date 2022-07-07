package com.xinbo.chainblock.controller.api;

import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.service.AgentService;
import com.xinbo.chainblock.service.StatisticsService;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/agent")
public class AgentController {


    @Autowired
    private AgentService agentService;

    @Autowired
    private StatisticsService statisticsService;

    @Operation(summary = "getCommission", description = "代理佣金")
    @PostMapping("getCommission")
    public R<Object> register() {

        MemberEntity entity = MemberEntity.builder().id(2).build();
        AgentEntity agentEntity = agentService.findByUid(entity.getId());
        if (ObjectUtils.isEmpty(agentEntity) || agentEntity.getId() <= 0) {
            return R.builder().data(StatusCode.FAILURE).build();
        }

        String child = agentEntity.getChild();
        if (StringUtils.isEmpty(child)) {
            return R.builder().data(StatusCode.SUCCESS).build();
        }

        String date = "20220704";

        String regex = ",";
        List<Integer> childList = Arrays.stream(child.split(regex)).map(Integer::parseInt).collect(Collectors.toList());
        childList.add(entity.getId());  //自己也加上


        List<StatisticsEntity> statisticsEntities = statisticsService.findByUidStr(date, childList);
        System.out.println(statisticsEntities);





        return null;
    }




    @Operation(summary = "myTeam", description = "我的团队")
    @PostMapping("myTeam")
    public R<Object> myTeam() {

        Map<String, Object> map = new HashMap<>();

        int uid = 2;

        AgentEntity agentEntity = agentService.findByUid(uid);

        //直属人数
        List<AgentEntity> direct = agentService.direct(uid);
        map.put("directNum", direct.size());

        //团队人数
        String child = agentEntity.getChild();
        long teamNum = 0;
        if(!StringUtils.isEmpty(child)) {
            teamNum = Arrays.stream(child.split(",")).count();
        }
        map.put("teamNum", teamNum);


        String date = DateUtil.format(new Date(), "yyyyMMdd");

        //今日下属业绩
        double subPerformance = 0;
        if(!StringUtils.isEmpty(child)) {
            String regex = ",";
            List<Integer> childList = Arrays.stream(child.split(regex)).map(Integer::parseInt).collect(Collectors.toList());
            List<StatisticsEntity> subList = statisticsService.findByUidStr(date, childList);
            if(!CollectionUtils.isEmpty(subList) && subList.size()>0) {
                subPerformance = subList.stream().mapToDouble(StatisticsEntity::getBetMoney).sum();
            }
        }
        map.put("subPerformance", subPerformance);

        //今日直属业绩
        double dirPerformance = 0;
        List<Integer> collect = direct.stream().map(AgentEntity::getUid).collect(Collectors.toList());
        List<StatisticsEntity> directList = statisticsService.findByUidStr(date, collect);
        if(!CollectionUtils.isEmpty(directList) && directList.size()>0) {
            dirPerformance = directList.stream().mapToDouble(StatisticsEntity::getBetMoney).sum();
        }
        map.put("dirPerformance", dirPerformance);



        return R.builder().code(StatusCode.SUCCESS).data(map).build();
    }


}
