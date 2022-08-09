package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.HashOddsDto;
import com.xinbo.chainblock.entity.hash.HashOddsEntity;
import com.xinbo.chainblock.service.HashOddsService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("ApiOddsController")
@RequestMapping("api/hashOdds")
public class HashOddsController {


    @Autowired
    private HashOddsService hashOddsService;



    @JwtIgnore
    @Operation(summary = "find", description = "查找单个")
    @GetMapping("find/{id}")
    public R<Object> find(@PathVariable int id) {
        HashOddsEntity entity = hashOddsService.findById(id);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(entity, HashOddsDto.class)).build();
    }

    @JwtIgnore
    @Operation(summary = "find", description = "查找")
    @GetMapping("findByGameId/{gameId}")
    public R<Object> findByGameId(@PathVariable int gameId) {
        List<HashOddsEntity> list = hashOddsService.findByGameId(gameId);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, HashOddsDto.class)).build();
    }

}
