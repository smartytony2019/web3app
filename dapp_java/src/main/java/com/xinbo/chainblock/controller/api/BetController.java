package com.xinbo.chainblock.controller.api;

import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.BetVo;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

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
    public R<Object> submit(@RequestBody @Valid BetVo vo) {
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

            LotteryBetEntity betEntity = LotteryBetEntity.builder()
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
                    .betOdds(playCodeEntity.getOdds())
                    .betMoney(vo.getBetMoney())
                    .createTime(new Date())
                    .build();

            boolean isSuccess = lotteryBetService.insert(betEntity);

            if(isSuccess) {
                r.setCode(StatusCode.SUCCESS);
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return r;
    }
}
