package com.xinbo.chainblock.controller.api;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.DateRangeBo;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.consts.GlobalConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.HashResultDto;
import com.xinbo.chainblock.entity.AgentCommissionEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.entity.hash.HashResultEntity;
import com.xinbo.chainblock.service.AgentCommissionService;
import com.xinbo.chainblock.service.HashResultService;
import com.xinbo.chainblock.service.StatisticsService;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.HashResultVo;
import com.xinbo.chainblock.vo.StatisticsVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tony
 * @date 9/7/22 9:27 下午
 * @desc 统计
 */
@RestController("ApiStatisticsController")
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private AgentCommissionService agentCommissionService;


    @JwtIgnore
    @Operation(summary = "financial", description = "财务报表")
    @PostMapping("financial/{current}/{size}")
    public R<Object> financial(@RequestBody StatisticsVo vo, @PathVariable long current, @PathVariable long size) {
        JwtUserBo jwtUser = JwtUtil.getJwtUser();
        DateRangeBo dateRangeBo = DateRangeBo.builder()
                .startTimeStr(StringUtils.isEmpty(vo.getStartTime()) ? DateUtil.date().toString(GlobalConst.DATE_YMD) : vo.getStartTime())
                .endTimeStr(StringUtils.isEmpty(vo.getEndTime()) ? DateUtil.date().toString(GlobalConst.DATE_YMD) : vo.getEndTime())
                .build();

        BasePageBo basePageBo = statisticsService.findPage(dateRangeBo, jwtUser.getUid(), current, size);
        StatisticsEntity total = statisticsService.findTotal(dateRangeBo, jwtUser.getUid());

        JSONObject result = new JSONObject();
        result.put("data", basePageBo);
        result.put("total", total);
        return R.builder().code(StatusCode.SUCCESS).data(result).build();
    }


    @JwtIgnore
    @Operation(summary = "promote", description = "推广报表")
    @PostMapping("promote/{current}/{size}")
    public R<Object> promote(@RequestBody StatisticsVo vo, @PathVariable long current, @PathVariable long size) {
        JwtUserBo jwtUser = JwtUtil.getJwtUser();
        DateRangeBo dateRangeBo = DateRangeBo.builder()
                .startTimeStr(StringUtils.isEmpty(vo.getStartTime()) ? DateUtil.date().toString(GlobalConst.DATE_YMD) : vo.getStartTime())
                .endTimeStr(StringUtils.isEmpty(vo.getEndTime()) ? DateUtil.date().toString(GlobalConst.DATE_YMD) : vo.getEndTime())
                .build();

        BasePageBo basePageBo = agentCommissionService.findPage(dateRangeBo, jwtUser.getUid(), current, size);
        AgentCommissionEntity total = agentCommissionService.findTotal(dateRangeBo, jwtUser.getUid());

        JSONObject result = new JSONObject();
        result.put("data", basePageBo);
        result.put("total", total);
        return R.builder().code(StatusCode.SUCCESS).data(result).build();
    }
}
