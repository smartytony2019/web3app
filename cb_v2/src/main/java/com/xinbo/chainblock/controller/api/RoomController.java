package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.HashGameDto;
import com.xinbo.chainblock.dto.HashRoomDto;
import com.xinbo.chainblock.entity.HashGameEntity;
import com.xinbo.chainblock.entity.HashRoomEntity;
import com.xinbo.chainblock.service.HashGameService;
import com.xinbo.chainblock.service.HashRoomService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("ApiRoomController")
@RequestMapping("api/room")
public class RoomController {


    @Autowired
    private HashRoomService hashRoomService;

    @JwtIgnore
    @Operation(summary = "find", description = "查找单个")
    @PostMapping("find/{id}")
    public R<Object> find(@PathVariable int id) {
        HashRoomEntity entity = hashRoomService.findById(id);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(entity, HashRoomDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "findAll", description = "查找所有")
    @PostMapping("findAll")
    public R<Object> findAll() {
        List<HashRoomEntity> list = hashRoomService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, HashRoomDto.class)).build();
    }

}
