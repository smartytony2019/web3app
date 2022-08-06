package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.GameDto;
import com.xinbo.chainblock.entity.GameEntity;
import com.xinbo.chainblock.service.HashGameService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("ApiCategoryController")
@RequestMapping("api/category")
public class CategoryController {


    @Autowired
    private HashGameService hashGameService;

    @JwtIgnore
    @Operation(summary = "find", description = "查找单个")
    @PostMapping("find/{id}")
    public R<Object> find(@PathVariable int id) {
        GameEntity entity = hashGameService.findById(id);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(entity, GameDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "findAll", description = "查找所有")
    @PostMapping("findAll")
    public R<Object> findAll() {
        List<GameEntity> list = hashGameService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, GameDto.class)).build();
    }

}
