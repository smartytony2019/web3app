package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.HashOddsDto;
import com.xinbo.chainblock.dto.HashRoomDto;
import com.xinbo.chainblock.entity.HashOddsEntity;
import com.xinbo.chainblock.entity.HashRoomEntity;
import com.xinbo.chainblock.service.HashOddsService;
import com.xinbo.chainblock.service.HashRoomService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("ApiOddsController")
@RequestMapping("api/odds")
public class OddsController {


    @Autowired
    private HashOddsService hashOddsService;



    @JwtIgnore
    @Operation(summary = "find", description = "查找单个")
    @PostMapping("find/{id}")
    public R<Object> find(@PathVariable int id) {
        HashOddsEntity entity = hashOddsService.findById(id);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(entity, HashOddsDto.class)).build();
    }

    @JwtIgnore
    @Operation(summary = "find", description = "查找")
    @PostMapping("findByGameId/{gameId}")
    public R<Object> findByGameId(@PathVariable int gameId) {
        List<HashOddsEntity> list = hashOddsService.findByGameId(gameId);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, HashOddsDto.class)).build();
    }

}
