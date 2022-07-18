package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.HashGameDto;
import com.xinbo.chainblock.entity.HashGameEntity;
import com.xinbo.chainblock.service.HashGameService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("ApiGameController")
@RequestMapping("api/game")
public class GameController {


    @Autowired
    private HashGameService hashGameService;

    @JwtIgnore
    @Operation(summary = "find", description = "查找单个")
    @PostMapping("find/{id}")
    public R<Object> find(@PathVariable int id) {
        HashGameEntity entity = hashGameService.findById(id);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(entity, HashGameDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "findAll", description = "查找所有")
    @PostMapping("findAll")
    public R<Object> findAll() {
        List<HashGameEntity> list = hashGameService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, HashGameDto.class)).build();
    }

}
