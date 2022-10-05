package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.annotation.RequiredPermission;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.GameDto;
import com.xinbo.chainblock.dto.HashOddsDto;
import com.xinbo.chainblock.dto.HashPlayDto;
import com.xinbo.chainblock.dto.HashResultDto;
import com.xinbo.chainblock.entity.GameEntity;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.hash.*;
import com.xinbo.chainblock.enums.HashBetResultEnum;
import com.xinbo.chainblock.enums.HashBetStatusEnum;
import com.xinbo.chainblock.enums.PermissionCodeEnum;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.*;
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


    @Autowired
    private HashPlayService hashPlayService;

    @Autowired
    private HashOddsService hashOddsService;

    @Autowired
    private HashResultService hashResultService;


    @JwtIgnore
    @Operation(summary = "findPlay", description = "玩法列表")
    @PostMapping("findPlay")
    public R<Object> findPlay() {
        List<HashPlayEntity> list = hashPlayService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, HashPlayDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "findOdds", description = "赔率列表")
    @PostMapping("findOdds/{gameId}")
    public R<Object> findOdds(@PathVariable int gameId) {
        List<HashOddsEntity> list = hashOddsService.findByGameId(gameId);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, HashOddsDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "findResult", description = "开奖列表")
    @PostMapping("findResult/{current}/{size}")
    public R<Object> findResult(@RequestBody HashResultVo vo, @PathVariable long current, @PathVariable long size) {
        HashResultEntity entity = MapperUtil.to(vo, HashResultEntity.class);
        BasePageBo basePageBo = hashResultService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }


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
