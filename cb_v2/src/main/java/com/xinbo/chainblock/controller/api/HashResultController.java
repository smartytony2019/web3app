package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.HashResultDto;
import com.xinbo.chainblock.entity.hash.HashResultEntity;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.HashResultVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("ApiHashResultController")
@RequestMapping("/api/hashResult")
public class HashResultController {


    @Autowired
    private HashResultService hashResultService;


    @JwtIgnore
    @Operation(summary = "findRecord", description = "查询开奖记录")
    @PostMapping("findRecord")
    public R<Object> findRecord(@RequestBody HashResultVo vo) {

        JwtUserBo jwtUser = JwtUtil.getJwtUser();
        HashResultEntity entity = MapperUtil.to(vo, HashResultEntity.class);
        entity.setUid(jwtUser.getUid());

        List<HashResultEntity> list = hashResultService.findRecord(entity);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(list, HashResultDto.class)).build();
    }

}
