package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.entity.UserEntity;
import com.xinbo.chainblock.service.AgentService;
import com.xinbo.chainblock.service.StatisticsService;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
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

        UserEntity entity = UserEntity.builder().id(2).build();
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


}
