package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.service.MemberFlowService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.MemberFlowVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author tony
 * @date 7/11/22 7:24 下午
 * @desc file desc
 */
@RestController("adminMemberFlowController")
@RequestMapping("/admin/memberFlow")
public class MemberFlowController {

    @Autowired
    private MemberFlowService memberFlowService;

    @JwtIgnore
    @Operation(summary = "findPage", description = "会员列表")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody MemberFlowVo vo, @PathVariable long current, @PathVariable long size) {
        MemberFlowEntity entity = MapperUtil.to(vo, MemberFlowEntity.class);
        BasePage basePage = memberFlowService.findPage(entity, current, size, vo.getStart(), vo.getEnd());
        return R.builder().code(StatusCode.SUCCESS).data(basePage).build();
    }


}
