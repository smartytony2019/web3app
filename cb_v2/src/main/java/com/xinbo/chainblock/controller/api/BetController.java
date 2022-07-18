package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.CommonUtils;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.BetSubmitVo;
import com.xinbo.chainblock.vo.BetVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/apibet")
public class BetController {

    @Autowired
    private HashGameService hashGameService;

    @Autowired
    private HashRoomService hashRoomService;

    @Autowired
    private HashOddsService hashOddsService;

    @Autowired
    private HashBetService hashBetService;

    @Operation(summary = "submit")
    @PostMapping("submit")
    public R<Object> submit(@RequestBody @Valid BetSubmitVo vo) {
        R<Object> r = R.builder().code(StatusCode.FAILURE).build();

        try {
            //判断数据是否合法
            MemberEntity memberEntity = MemberEntity.builder()
                    .username("jackC")
                    .id(3)
                    .version(1)
                    .build();


            HashGameEntity hashGameEntity = hashGameService.findById(vo.getGameId());
            if(ObjectUtils.isEmpty(hashGameEntity) || hashGameEntity.getId()<=0) {
                throw new RuntimeException("lottery game not found");
            }

            HashRoomEntity playEntity = hashRoomService.findById(vo.getPlayId());
            if(ObjectUtils.isEmpty(playEntity) || playEntity.getId()<=0) {
                throw new RuntimeException("lottery play not found");
            }

            HashOddsEntity playHashOddsEntity = hashOddsService.findById(vo.getPlayCodeId());
            if(ObjectUtils.isEmpty(playEntity) || playEntity.getId()<=0) {
                throw new RuntimeException("lottery play code not found");
            }

            HashBetEntity entity = HashBetEntity.builder()
                    .build();

            boolean isSuccess = hashBetService.insert(entity);
            if(isSuccess) {
                r.setCode(StatusCode.SUCCESS);
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }


    @Operation(summary = "find", description = "获取注单")
    @PostMapping("find")
    public R<Object> find(@RequestBody BetVo vo) {
        HashBetEntity entity = MapperUtil.to(vo, HashBetEntity.class);
        List<HashBetEntity> lotteryBetDtoList = hashBetService.find(entity);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(lotteryBetDtoList, HashBetEntity.class)).build();
    }


    @Operation(summary = "findPage", description = "获取注单")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody BetVo vo, @PathVariable long current, @PathVariable long size) {
        HashBetEntity entity = MapperUtil.to(vo, HashBetEntity.class);
        BasePage basePage = hashBetService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePage).build();
    }


    @Operation(summary = "test", description = "获取注单")
    @GetMapping("test/{language}/{key}")
    public R<Object> test(@PathVariable("language")String language, @PathVariable("key")String key) {
        String values = CommonUtils.translate(language, key);
        return R.builder().code(StatusCode.SUCCESS).data(values).build();
    }

}
