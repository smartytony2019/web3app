package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.GameEntity;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.hash.HashOfflineBetEntity;
import com.xinbo.chainblock.entity.hash.HashPlayEntity;
import com.xinbo.chainblock.enums.HashBetResultEnum;
import com.xinbo.chainblock.enums.HashBetStatusEnum;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.ActivityVo;
import com.xinbo.chainblock.vo.HashBetVo;
import com.xinbo.chainblock.vo.HashOfflineBetVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tony
 * @date 7/11/22 7:24 下午
 * @desc file desc
 */
@RestController("adminHashController")
@RequestMapping("/admin/hash")
public class HashController {

    @Autowired
    private HashBetService hashBetService;

    @Autowired
    private HashOfflineBetService hashOfflineBetService;


    @JwtIgnore
    @Operation(summary = "findBetStatus", description = "查找注单状态")
    @PostMapping("findBetStatus")
    public R<Object> findBetStatus() {
        return R.builder().code(StatusCode.SUCCESS).data(HashBetStatusEnum.toList()).build();
    }

    @JwtIgnore
    @Operation(summary = "findBetResult", description = "查找注单结果")
    @PostMapping("findBetResult")
    public R<Object> findBetResult() {
        return R.builder().code(StatusCode.SUCCESS).data(HashBetResultEnum.toList()).build();
    }


    @JwtIgnore
    @Operation(summary = "findBetPage", description = "查找分页")
    @PostMapping("findBetPage/{current}/{size}")
    public R<Object> findBetPage(@RequestBody HashBetVo vo, @PathVariable long current, @PathVariable long size) {
        HashBetEntity entity = MapperUtil.to(vo, HashBetEntity.class);
        BasePageBo basePageBo = hashBetService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }


    @JwtIgnore
    @Operation(summary = "findOfflineBetPage", description = "查找分页")
    @PostMapping("findOfflineBetPage/{current}/{size}")
    public R<Object> findOfflineBetPage(@RequestBody HashOfflineBetVo vo, @PathVariable long current, @PathVariable long size) {
        HashOfflineBetEntity entity = MapperUtil.to(vo, HashOfflineBetEntity.class);
        BasePageBo basePageBo = hashOfflineBetService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }



}
