package com.xinbo.chainblock.controller.api;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinbo.chainblock.annotation.Log;
import com.xinbo.chainblock.annotation.RedisHandel;
import com.xinbo.chainblock.annotation.Translate;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.LotteryBetDto;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.CommonUtils;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.BetSubmitVo;
import com.xinbo.chainblock.vo.BetVo;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/bet")
public class BetController {

    @Autowired
    private LotteryGameService lotteryGameService;

    @Autowired
    private LotteryPlayService lotteryPlayService;

    @Autowired
    private LotteryPlayCodeService lotteryPlayCodeService;

    @Autowired
    private LotteryBetService lotteryBetService;

    @Operation(summary = "submit")
    @PostMapping("submit")
    public R<Object> submit(@RequestBody @Valid BetSubmitVo vo) {
        R<Object> r = R.builder().code(StatusCode.FAILURE).build();

        try {
            //判断数据是否合法
            UserEntity userEntity = UserEntity.builder()
                    .username("jack")
                    .id(1)
                    .version(1)
                    .build();


            LotteryGameEntity gameEntity = lotteryGameService.findById(vo.getGameId());
            if(ObjectUtils.isEmpty(gameEntity) || gameEntity.getId()<=0) {
                throw new RuntimeException("lottery game not found");
            }

            LotteryPlayEntity playEntity = lotteryPlayService.findById(vo.getPlayId());
            if(ObjectUtils.isEmpty(playEntity) || playEntity.getId()<=0) {
                throw new RuntimeException("lottery play not found");
            }

            LotteryPlayCodeEntity playCodeEntity = lotteryPlayCodeService.findById(vo.getPlayCodeId());
            if(ObjectUtils.isEmpty(playEntity) || playEntity.getId()<=0) {
                throw new RuntimeException("lottery play code not found");
            }

            LotteryBetDto betDto = LotteryBetDto.builder()
                    .uid(userEntity.getId())
                    .username(userEntity.getUsername())
                    .cateId(gameEntity.getCateId())
                    .cateNameCode(gameEntity.getCateNameCode())
                    .cateNameDefault(gameEntity.getCateNameDefault())
                    .gameId(gameEntity.getId())
                    .gameNameCode(gameEntity.getNameCode())
                    .gameNameDefault(gameEntity.getNameDefault())
                    .playId(playEntity.getId())
                    .playNameCode(playEntity.getNameCode())
                    .playNameDefault(playEntity.getNameDefault())
                    .playCodeId(playCodeEntity.getId())
                    .playCodeNameCode(playCodeEntity.getNameCode())
                    .playCodeNameDefault(playCodeEntity.getNameDefault())
                    .num(vo.getNum())
                    .odds(playCodeEntity.getOdds())
                    .money(vo.getMoney())
                    .createTime(new Date())
                    .build();

            boolean isSuccess = lotteryBetService.insert(betDto);
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
        LotteryBetDto dto = LotteryBetDto.builder()
                .gameId(vo.getGameId())
                .build();
        List<LotteryBetDto> lotteryBetDtoList = lotteryBetService.find(dto);
        return R.builder().data(lotteryBetDtoList).build();
    }

    @Operation(summary = "test", description = "获取注单")
    @GetMapping("test/{language}/{key}")
    public R<Object> test(@PathVariable("language")String language, @PathVariable("key")String key) {
        String values = CommonUtils.translate(language, key);
        return R.builder().data(values).build();
    }

}
