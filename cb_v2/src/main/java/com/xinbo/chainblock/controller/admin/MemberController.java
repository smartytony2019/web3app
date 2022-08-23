package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.MemberDto;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.service.MemberFlowService;
import com.xinbo.chainblock.service.MemberService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.MemberFlowVo;
import com.xinbo.chainblock.vo.MemberVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author tony
 * @date 7/11/22 7:24 下午
 * @desc file desc
 */
@RestController("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberFlowService memberFlowService;

    @JwtIgnore
    @Operation(summary = "findPage", description = "会员列表")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody MemberDto vo, @PathVariable long current, @PathVariable long size) {
        MemberEntity entity = MapperUtil.to(vo, MemberEntity.class);
        BasePageBo basePageBo = memberService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }


    @JwtIgnore
    @Operation(summary = "findById", description = "查询会员")
    @GetMapping("findById/{id}")
    public R<Object> findById(@PathVariable Integer id) {
        MemberEntity entity = memberService.findById(id);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(entity, MemberDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "findById", description = "查询会员")
    @PostMapping("update")
    public R<Object> update(@RequestBody @Valid MemberVo vo) {
        MemberEntity entity = MapperUtil.to(vo, MemberEntity.class);
        entity.setSalt("11111");
        entity.setVersion(1);
        boolean isSuccess = memberService.update(entity);
        return R.builder().code(isSuccess?StatusCode.SUCCESS:StatusCode.FAILURE).build();
    }

    @JwtIgnore
    @Operation(summary = "findFlowPage", description = "会员流水")
    @PostMapping("findFlowPage/{current}/{size}")
    public R<Object> findFlowPage(@RequestBody MemberFlowVo vo, @PathVariable long current, @PathVariable long size) {
        MemberFlowEntity entity = MapperUtil.to(vo, MemberFlowEntity.class);
        BasePageBo basePageBo = memberFlowService.findPage(entity, current, size, vo.getStart(), vo.getEnd());
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }


}
