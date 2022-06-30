package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.modal.Do.*;
import com.xinbo.chainblock.modal.Dto.LotteryBetDto;
import com.xinbo.chainblock.modal.Vo.BetVo;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.modal.Vo.BetSubmitVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public R<Object> submit(@RequestBody @Valid BetSubmitVo vo) {
        R<Object> r = R.builder().code(StatusCode.FAILURE).build();

        try {
            //判断数据是否合法
            UserDo userEntity = UserDo.builder()
                    .username("jack")
                    .id(1)
                    .version(1)
                    .build();


            LotteryGameDo gameEntity = lotteryGameService.findById(vo.getGameId());
            if(ObjectUtils.isEmpty(gameEntity) || gameEntity.getId()<=0) {
                throw new RuntimeException("lottery game not found");
            }

            LotteryPlayDo playEntity = lotteryPlayService.findById(vo.getPlayId());
            if(ObjectUtils.isEmpty(playEntity) || playEntity.getId()<=0) {
                throw new RuntimeException("lottery play not found");
            }

            LotteryPlayCodeDo playCodeEntity = lotteryPlayCodeService.findById(vo.getPlayCodeId());
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
        R<Object> r = R.builder().build();

        LotteryBetDto dto = LotteryBetDto.builder()
                .gameId(vo.getGameId())
                .build();
        List<LotteryBetDto> lotteryBetDtoList = lotteryBetService.find(dto);

        return R.builder().data(lotteryBetDtoList).build();
    }

}
