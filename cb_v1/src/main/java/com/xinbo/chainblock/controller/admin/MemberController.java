package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.dto.MemberDto;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.service.MemberService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tony
 * @date 7/11/22 7:24 下午
 * @desc file desc
 */
@RestController("adminMember")
@RequestMapping("/admin/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @JwtIgnore
    @Operation(summary = "findPage", description = "会员列表")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody UserVo vo, @PathVariable long current, @PathVariable long size) {
        MemberEntity entity = MapperUtil.to(vo, MemberEntity.class);
        BasePage basePage = memberService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePage).build();
    }


    @JwtIgnore
    @Operation(summary = "findById", description = "查询会员")
    @GetMapping("findById/{id}")
    public R<Object> findById(@PathVariable Integer id) {
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(memberService.findById(id), MemberDto.class)).build();
    }

}
