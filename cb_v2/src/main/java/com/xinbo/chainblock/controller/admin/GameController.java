package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.CategoryDto;
import com.xinbo.chainblock.dto.GameDto;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.CategoryEntity;
import com.xinbo.chainblock.entity.GameEntity;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.hash.HashOfflineBetEntity;
import com.xinbo.chainblock.enums.HashBetResultEnum;
import com.xinbo.chainblock.enums.HashBetStatusEnum;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.AgentVo;
import com.xinbo.chainblock.vo.GameVo;
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
@RestController("adminGameController")
@RequestMapping("/admin/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private CategoryService categoryService;


    @JwtIgnore
    @Operation(summary = "findCategory", description = "查找类目")
    @PostMapping("findCategory")
    public R<Object> findCategory() {
        List<CategoryEntity> list = categoryService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, CategoryDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "findPage", description = "游戏列表")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody GameVo vo, @PathVariable long current, @PathVariable long size) {
        GameEntity entity = MapperUtil.to(vo, GameEntity.class);
        BasePageBo basePageBo = gameService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }


    @JwtIgnore
    @Operation(summary = "update", description = "游戏更新")
    @PostMapping("update")
    public R<Object> update(@RequestBody GameVo gameVo) {
        boolean isSuccess = gameService.update(MapperUtil.to(gameVo, GameEntity.class));
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

}
