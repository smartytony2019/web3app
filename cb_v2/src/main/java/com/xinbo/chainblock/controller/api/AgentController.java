package com.xinbo.chainblock.controller.api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.GlobalConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.enums.MemberFlowItemEnum;
import com.xinbo.chainblock.exception.BusinessException;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController("ApiAgentController")
@RequestMapping("api/agent")
public class AgentController {


    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentRebateService agentRebateService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AgentCommissionService agentCommissionService;

    @Autowired
    private StatisticsService statisticsService;

    @Operation(summary = "getCommission", description = "代理佣金")
    @PostMapping("getCommission")
    public R<Object> getCommission() {

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


    @JwtIgnore
    @Operation(summary = "myTeam", description = "我的团队")
    @PostMapping("myTeam")
    public R<Object> myTeam() {
        JSONObject result = new JSONObject();

        int uid = 2;

        AgentEntity agentEntity = agentService.findByUid(uid);

        //直属人数
        List<AgentEntity> direct = agentService.direct(uid);
        result.put("directNum", direct.size());

        //团队人数
        String child = agentEntity.getChild();
        long teamNum = 0;
        if (!StringUtils.isEmpty(child)) {
            teamNum = Arrays.stream(child.split(",")).count();
        }
        result.put("teamNum", teamNum);


        String date = DateUtil.format(new Date(), GlobalConst.DATE_YMD);

        //今日下属业绩
        double subPerformance = 0;
        if (!StringUtils.isEmpty(child)) {
            String regex = ",";
            List<Integer> childList = Arrays.stream(child.split(regex)).map(Integer::parseInt).collect(Collectors.toList());
            List<StatisticsEntity> subList = statisticsService.findByUidStr(date, childList);
            if (!CollectionUtils.isEmpty(subList) && subList.size() > 0) {
                subPerformance = subList.stream().mapToDouble(StatisticsEntity::getBetAmount).sum();
            }
        }
        result.put("subPerformance", subPerformance);

        //今日直属业绩
        double dirPerformance = 0;
        List<Integer> collect = direct.stream().map(AgentEntity::getUid).collect(Collectors.toList());
        List<StatisticsEntity> directList = statisticsService.findByUidStr(date, collect);
        if (!CollectionUtils.isEmpty(directList) && directList.size() > 0) {
            dirPerformance = directList.stream().mapToDouble(StatisticsEntity::getBetAmount).sum();
        }
        result.put("directPerformance", dirPerformance);


        String yesterday = DateUtil.yesterday().toString(GlobalConst.DATE_YMD);
        AgentCommissionEntity entity = AgentCommissionEntity.builder()
                .uid(uid)
                .date(yesterday)
                .build();
        AgentCommissionEntity commissionEntity = agentCommissionService.find(entity);
        float selfCommission = 0F;
        float totalCommission = 0F;
        if (!ObjectUtils.isEmpty(commissionEntity)) {
            selfCommission = commissionEntity.getSelfCommission();
            totalCommission = commissionEntity.getTotalCommission();
        }

        //昨日佣金
        result.put("yesterdayCommission", selfCommission);


        //昨日总佣金
        result.put("yesterdayCommissionTotal", totalCommission);


        float availableCommission = agentCommissionService.findAvailableCommission(uid);

        //可用佣金
        result.put("availableCommission", availableCommission);

        //佣金合计
        float commissionTotal = agentCommissionService.findCommissionTotal(uid);
        result.put("commissionTotal", commissionTotal);

        return R.builder().code(StatusCode.SUCCESS).data(result).build();
    }


    @JwtIgnore
    @Operation(summary = "myPerformance", description = "我的业绩")
    @PostMapping("myPerformance")
    public R<Object> myPerformance() {
        JSONObject result = new JSONObject();

        int uid = 2;
        AgentEntity agentEntity = agentService.findByUid(uid);

        // 直属人数
        List<AgentEntity> direct = agentService.direct(uid);
        result.put("directNum", direct.size());

        // 团队人数
        String child = agentEntity.getChild();
        long teamNum = 0;
        if (!StringUtils.isEmpty(child)) {
            teamNum = Arrays.stream(child.split(",")).count();
        }
        result.put("teamNum", teamNum);

        // 今日总业绩
        AgentCommissionEntity commissionEntity = AgentCommissionEntity.builder()
                .date(DateUtil.date().toString(GlobalConst.DATE_YMD))
                .uid(uid)
                .build();
        AgentCommissionEntity agentCommissionEntity = agentCommissionService.find(commissionEntity);
        result.put("todayPerformanceTotal", agentCommissionEntity.getTotalPerformance());

        return R.builder().code(StatusCode.SUCCESS).data(result).build();
    }



    @JwtIgnore
    @Operation(summary = "teamPerformance", description = "团队业绩")
    @PostMapping("teamPerformance/{date}")
    public R<Object> teamPerformance(@PathVariable String date) {
        JSONObject result = new JSONObject();

        int uid = 2;
        AgentCommissionEntity entity = agentCommissionService.find(uid, date);
        result.put("teamPerformance", entity.getTeamPerformance());
        result.put("selfPerformance", entity.getSelfPerformance());
        result.put("directPerformance", entity.getDirectPerformance());
        result.put("subPerformance", entity.getSubPerformance());
        result.put("totalCommission", entity.getTotalCommission());

        return R.builder().code(StatusCode.SUCCESS).data(result).build();
    }



    @JwtIgnore
    @Operation(summary = "rebate", description = "代理返佣表")
    @PostMapping("rebate")
    public R<Object> rebate() {
        List<AgentRebateEntity> all = agentRebateService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(all).build();
    }



    @JwtIgnore
    @Operation(summary = "applySubmit", description = "申请佣金")
    @PostMapping("applySubmit")
    public R<Object> applySubmit() {
        try {
            int uid = 2;
            // 查询可用佣金(未申请)
            float totalCommission = agentCommissionService.findAvailableCommission(uid);
            if (totalCommission <= 0) {
                throw new BusinessException(1, "没有佣金可申请");
            }

            MemberEntity memberEntity = memberService.findById(uid);
            if (ObjectUtils.isEmpty(memberEntity)) {
                throw new BusinessException(1, "会员不存在");
            }

            String sn = IdUtil.getSnowflake().nextIdStr();

            // Step 2.1: 会员表
            MemberEntity member = MemberEntity.builder()
                    .money(totalCommission)
                    .id(memberEntity.getId())
                    .version(memberEntity.getVersion())
                    .build();


            // Step 2.2: 会员流水表
            MemberFlowEntity memberFlow = MemberFlowEntity.builder()
                    .sn(sn)
                    .uid(memberEntity.getId())
                    .username(memberEntity.getUsername())
                    .beforeMoney(memberEntity.getMoney())
                    .afterMoney(memberEntity.getMoney() + totalCommission)
                    .flowMoney(totalCommission)
                    .item(MemberFlowItemEnum.AGENT_COMMISSION.getName())
                    .itemCode(MemberFlowItemEnum.AGENT_COMMISSION.getCode())
                    .itemZh(MemberFlowItemEnum.AGENT_COMMISSION.getNameZh())
                    .createTime(DateUtil.date())
                    .build();

            // Step 2.2: 代理佣金记录表
            AgentCommissionRecordEntity record = AgentCommissionRecordEntity.builder()
                    .sn(sn)
                    .uid(memberEntity.getId())
                    .username(memberEntity.getUsername())
                    .money(totalCommission)
                    .createTime(DateUtil.date())
                    .createTimestamp(DateUtil.current())
                    .build();

            boolean isSuccess = agentCommissionService.applySubmit(uid, record, member, memberFlow);
            return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return R.builder().code(StatusCode.FAILURE).msg("申请失败").build();
        }
    }
}
