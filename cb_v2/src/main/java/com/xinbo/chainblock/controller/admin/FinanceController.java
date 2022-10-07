package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.AgentDomainDto;
import com.xinbo.chainblock.dto.AgentDto;
import com.xinbo.chainblock.dto.AgentRebateDto;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.AgentCommissionRecordVo;
import com.xinbo.chainblock.vo.AgentCommissionVo;
import com.xinbo.chainblock.vo.AgentVo;
import com.xinbo.chainblock.vo.FinanceVo;
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
@RestController("AdminFinanceController")
@RequestMapping("/admin/finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;


    @JwtIgnore
    @Operation(summary = "findPage", description = "财务列表(分页)")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody FinanceVo vo, @PathVariable long current, @PathVariable long size) {
        FinanceEntity entity = MapperUtil.to(vo, FinanceEntity.class);
        BasePageBo basePageBo = financeService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }



}
