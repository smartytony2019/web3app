package com.xinbo.chainblock.controller.api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.consts.AgentConst;
import com.xinbo.chainblock.consts.GlobalConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.enums.MemberFlowItemEnum;
import com.xinbo.chainblock.exception.BusinessException;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.AgentCommissionRecordVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    private  AgentCommissionRecordService agentCommissionRecordService;

    @Autowired
    private StatisticsService statisticsService;

    @Operation(summary = "find", description = "代理佣金")
    @PostMapping("find/{date}")
    public R<Object> find(@PathVariable String date) {

        JwtUserBo jwtUser = JwtUtil.getJwtUser();
        AgentEntity agentEntity = agentService.findByUid(jwtUser.getUid());
        if (ObjectUtils.isEmpty(agentEntity) || agentEntity.getId() <= 0) {
            return R.builder().data(StatusCode.FAILURE).build();
        }

        AgentCommissionEntity entity = agentCommissionService.find(jwtUser.getUid(), date);

        float commissionTotal = agentCommissionService.findCommissionTotal(jwtUser.getUid());
        float availableCommission = agentCommissionService.findAvailableCommission(jwtUser.getUid());


        JSONObject result = new JSONObject();

        result.put("directCount", entity.getDirectCount());
        result.put("teamCount", entity.getTeamCount());
        result.put("teamPerformance", entity.getTeamPerformance());
        result.put("totalPerformance", entity.getTotalPerformance());

        result.put("commissionTotal",commissionTotal);
        result.put("availableCommission",availableCommission);

        return R.builder().code(StatusCode.SUCCESS).data(result).build();
    }

    @JwtIgnore
    @Operation(summary = "myTeam", description = "我的团队")
    @PostMapping("myTeam")
    public R<Object> myTeam() {
        JSONObject result = new JSONObject();

        JwtUserBo jwtUser = JwtUtil.getJwtUser();

        AgentEntity agentEntity = agentService.findByUid(jwtUser.getUid());

        //直属人数
        List<AgentEntity> direct = agentService.direct(jwtUser.getUid());
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
                .uid(jwtUser.getUid())
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


        float availableCommission = agentCommissionService.findAvailableCommission(jwtUser.getUid());

        //可用佣金
        result.put("availableCommission", availableCommission);

        //佣金合计
        float commissionTotal = agentCommissionService.findCommissionTotal(jwtUser.getUid());
        result.put("commissionTotal", commissionTotal);

        return R.builder().code(StatusCode.SUCCESS).data(result).build();
    }


    @JwtIgnore
    @Operation(summary = "myPerformance", description = "我的业绩")
    @PostMapping("myPerformance")
    public R<Object> myPerformance() {
        JSONObject result = new JSONObject();

        JwtUserBo jwtUser = JwtUtil.getJwtUser();
        AgentEntity agentEntity = agentService.findByUid(jwtUser.getUid());

        // 直属人数
        List<AgentEntity> direct = agentService.direct(jwtUser.getUid());
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
                .uid(jwtUser.getUid())
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
        JwtUserBo jwtUser = JwtUtil.getJwtUser();
        AgentCommissionEntity entity = agentCommissionService.find(jwtUser.getUid(), date);
        if(!ObjectUtils.isEmpty(entity)) {
            result.put("teamPerformance", entity.getTeamPerformance());
            result.put("selfPerformance", entity.getSelfPerformance());
            result.put("directPerformance", entity.getDirectPerformance());
            result.put("subPerformance", entity.getSubPerformance());
            result.put("totalCommission", entity.getTotalCommission());
        }
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
            JwtUserBo jwtUser = JwtUtil.getJwtUser();

            // 查询可用佣金(未申请)
            float totalCommission = agentCommissionService.findAvailableCommission(jwtUser.getUid());
            if (totalCommission <= 0) {
                throw new BusinessException(1, "没有佣金可申请");
            }

            MemberEntity memberEntity = memberService.findById(jwtUser.getUid());
            if (ObjectUtils.isEmpty(memberEntity)) {
                throw new BusinessException(1, "会员不存在");
            }

            String sn = IdUtil.getSnowflake().nextIdStr();

            // Step 2.2: 代理佣金记录表
            AgentCommissionRecordEntity entity = AgentCommissionRecordEntity.builder()
                    .sn(sn)
                    .uid(memberEntity.getId())
                    .username(memberEntity.getUsername())
                    .money(totalCommission)
                    .createTime(DateUtil.date())
                    .createTimestamp(DateUtil.current())
                    .status(AgentConst.COMMISSION_RECORD_STATUS_APPLY)
                    .build();

            boolean isSuccess = agentCommissionService.applySubmit(entity);
            return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return R.builder().code(StatusCode.FAILURE).msg("申请失败").build();
        }
    }


    @JwtIgnore
    @Operation(summary = "handle", description = "申请佣金")
    @PostMapping("handle")
    public R<Object> handle(@RequestBody AgentCommissionRecordVo vo) {

        // 佣金申请通过
        if(vo.getStatus() == AgentConst.COMMISSION_RECORD_STATUS_PASS) {
            AgentCommissionRecordEntity entity = agentCommissionRecordService.findById(vo.getId());
            MemberEntity memberEntity = memberService.findById(entity.getUid());

            float money = entity.getMoney();
            // Step 2.1: 会员表
            MemberEntity member = MemberEntity.builder()
                    .money(money)
                    .id(memberEntity.getId())
                    .version(memberEntity.getVersion())
                    .build();


            // Step 2.2: 会员流水表
            MemberFlowEntity memberFlow = MemberFlowEntity.builder()
                    .sn(entity.getSn())
                    .uid(memberEntity.getId())
                    .username(memberEntity.getUsername())
                    .beforeMoney(memberEntity.getMoney())
                    .afterMoney(memberEntity.getMoney() + money)
                    .flowMoney(money)
                    .item(MemberFlowItemEnum.AGENT_COMMISSION.getName())
                    .itemCode(MemberFlowItemEnum.AGENT_COMMISSION.getCode())
                    .itemZh(MemberFlowItemEnum.AGENT_COMMISSION.getNameZh())
                    .createTime(DateUtil.date())
                    .build();

            // Step 2.3: 统计表
            StatisticsEntity statistics = StatisticsEntity.builder()
                    .date(DateUtil.format(new Date(), GlobalConst.DATE_YMD))
                    .uid(memberEntity.getId())
                    .username(memberEntity.getUsername())
                    .betAmount(0F)
                    .betCount(0)
                    .profitAmount(0F)
                    .rechargeTrc20Count(0)
                    .rechargeTrc20Amount(0F)
                    .withdrawTrc20Amount(0F)
                    .rechargeTrxCount(0)
                    .rechargeTrxAmount(0F)
                    .withdrawTrxAmount(0F)
                    .commissionAmount(money)
                    .activityAmount(0F)
                    .updateTime(new Date())
                    .build();

            entity.setStatus(AgentConst.COMMISSION_RECORD_STATUS_PASS);
            boolean isSuccess = agentCommissionRecordService.handle(entity, member, memberFlow, statistics);
            System.out.println(isSuccess);
        }

        // 佣金申请拒绝
        if(vo.getStatus() == AgentConst.COMMISSION_RECORD_STATUS_REJECT) {

        }

        return R.builder().code(StatusCode.SUCCESS).build();
    }


}
