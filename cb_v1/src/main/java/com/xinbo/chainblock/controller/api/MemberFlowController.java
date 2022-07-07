package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.RequiredPermission;
import com.xinbo.chainblock.consts.PermissionConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.UserFlowVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/memberFlow")
public class MemberFlowController {

    @Autowired
    private MemberFlowService memberFlowService;

    @Operation(summary = "findPage", description = "获取注单")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody UserFlowVo vo, @PathVariable long current, @PathVariable long size) {
        MemberFlowEntity entity = MapperUtil.to(vo, MemberFlowEntity.class);
        BasePage basePage = memberFlowService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePage).build();
    }

    @Operation(summary = "test1", description = "测试")
    @PostMapping("test1")
    @RequiredPermission(PermissionConst.test1)
    public R<Object> test1() {
        return R.builder().code(StatusCode.SUCCESS).data("success").build();
    }


    @Operation(summary = "test2", description = "测试")
    @PostMapping("test2")
    @RequiredPermission(PermissionConst.test2)
    public R<Object> test2() {
        return R.builder().code(StatusCode.SUCCESS).data("success").build();
    }

}
