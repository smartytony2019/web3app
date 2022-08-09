package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.HashPlayDto;
import com.xinbo.chainblock.entity.hash.HashPlayEntity;
import com.xinbo.chainblock.service.HashPlayService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("ApiHashPlayController")
@RequestMapping("api/hashPlay")
public class HashPlayController {


    @Autowired
    private HashPlayService hashPlayService;

    @JwtIgnore
    @Operation(summary = "find", description = "查找单个")
    @GetMapping("find/{id}")
    public R<Object> find(@PathVariable int id) {
        HashPlayEntity entity = hashPlayService.findById(id);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(entity, HashPlayDto.class)).build();
    }

    @JwtIgnore
    @Operation(summary = "findByGameId", description = "查找")
    @GetMapping("findByGameId/{id}")
    public R<Object> findByGameId(@PathVariable int id) {
        List<HashPlayEntity> list = hashPlayService.findByGameId(id);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, HashPlayDto.class)).build();
    }


    @JwtIgnore
    @Operation(summary = "findAll", description = "查找所有")
    @PostMapping("findAll")
    public R<Object> findAll() {
        List<HashPlayEntity> list = hashPlayService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, HashPlayDto.class)).build();
    }

}
