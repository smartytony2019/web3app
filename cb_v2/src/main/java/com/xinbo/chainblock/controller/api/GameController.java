package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.GameDto;
import com.xinbo.chainblock.entity.GameEntity;
import com.xinbo.chainblock.service.GameService;
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
    private GameService gameService;

    @JwtIgnore
    @Operation(summary = "find", description = "查找单个")
    @GetMapping("find/{id}")
    public R<Object> find(@PathVariable int id) {
        GameEntity entity = gameService.findById(id);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(entity, GameDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "findAll", description = "查找所有")
    @GetMapping("findAll")
    public R<Object> findAll() {
        List<GameEntity> list = gameService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, GameDto.class)).build();
    }

}
