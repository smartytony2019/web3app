package com.xinbo.chainblock.controller.api;

import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.DateRangeBo;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.consts.GlobalConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.HashResultDto;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.entity.hash.HashResultEntity;
import com.xinbo.chainblock.service.HashResultService;
import com.xinbo.chainblock.service.StatisticsService;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.HashResultVo;
import com.xinbo.chainblock.vo.StatisticsVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @JwtIgnore
    @Operation(summary = "financial", description = "财务报表")
    @PostMapping("financial")
    public R<Object> financial(@RequestBody StatisticsVo vo) {
        JwtUserBo jwtUser = JwtUtil.getJwtUser();
        DateRangeBo dateRangeBo = DateRangeBo.builder()
                .startTimeStr(DateUtil.date(vo.getStartTime()).toString(GlobalConst.DATE_YMD))
                .endTimeStr(DateUtil.date(vo.getEndTime()).toString(GlobalConst.DATE_YMD))
                .build();

        List<StatisticsEntity> list = statisticsService.findList(dateRangeBo, jwtUser.getUid());

        StatisticsEntity total = statisticsService.findTotal(dateRangeBo, jwtUser.getUid());

        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, HashResultDto.class)).build();
    }
}
