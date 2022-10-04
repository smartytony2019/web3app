package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.AgentDomainDto;
import com.xinbo.chainblock.dto.AgentDto;
import com.xinbo.chainblock.dto.AgentRebateDto;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.AgentCommissionRecordVo;
import com.xinbo.chainblock.vo.AgentCommissionVo;
import com.xinbo.chainblock.vo.AgentVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tony
 * @date 7/11/22 7:24 下午
 * @desc file desc
 */
@RestController("AdminAgentController")
@RequestMapping("/admin/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentRebateService agentRebateService;

    @Autowired
    private AgentDomainService agentDomainService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AgentCommissionService agentCommissionService;

    @Autowired
    private AgentCommissionRecordService agentCommissionRecordService;


    @JwtIgnore
    @Operation(summary = "findPage", description = "代理列表(分页)")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody AgentVo vo, @PathVariable long current, @PathVariable long size) {
        AgentEntity entity = MapperUtil.to(vo, AgentEntity.class);
        BasePageBo basePageBo = agentService.findPage(entity, current, size);

        List<AgentDto> records = (List<AgentDto>) basePageBo.getRecords();
        if (!ObjectUtils.isEmpty(records)) {
            for (AgentDto dto : records) {
                MemberEntity memberEntity = memberService.findById(dto.getPUid());
                if (ObjectUtils.isEmpty(memberEntity)) {
                    continue;
                }

                dto.setPUsername(memberEntity.getUsername());
            }
        }
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }


    @JwtIgnore
    @Operation(summary = "findRebate", description = "代理返佣比列表")
    @PostMapping("findRebate")
    public R<Object> findRebate() {
        List<AgentRebateEntity> list = agentRebateService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, AgentRebateDto.class)).build();
    }



    @JwtIgnore
    @Operation(summary = "findDomain", description = "代理推广域名")
    @PostMapping("findDomain")
    public R<Object> findDomain() {
        List<AgentDomainEntity> list = agentDomainService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, AgentDomainDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "findCommissionPage", description = "代理佣金列表(分页)")
    @PostMapping("findCommissionPage/{current}/{size}")
    public R<Object> findCommissionPage(@RequestBody AgentCommissionVo vo, @PathVariable long current, @PathVariable long size) {
        AgentCommissionEntity entity = MapperUtil.to(vo, AgentCommissionEntity.class);
        BasePageBo basePageBo = agentCommissionService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }

    @JwtIgnore
    @Operation(summary = "findCommissionRecordPage", description = "代理佣金领取记录列表(分页)")
    @PostMapping("findCommissionRecordPage/{current}/{size}")
    public R<Object> findCommissionRecordPage(@RequestBody AgentCommissionRecordVo vo, @PathVariable long current, @PathVariable long size) {
        AgentCommissionRecordEntity entity = MapperUtil.to(vo, AgentCommissionRecordEntity.class);
        BasePageBo basePageBo = agentCommissionRecordService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }

}
