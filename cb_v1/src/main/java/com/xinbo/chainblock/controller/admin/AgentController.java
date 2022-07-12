package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.LotteryBetEntity;
import com.xinbo.chainblock.service.LotteryBetService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.BetVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tony
 * @date 7/11/22 7:24 下午
 * @desc file desc
 */
@RestController("adminBetController")
@RequestMapping("/admin/bet")
public class AgentController {

    @Autowired
    private LotteryBetService lotteryBetService;


    @JwtIgnore
    @Operation(summary = "findLotteryPage", description = "注单列表")
    @PostMapping("findLotteryPage/{current}/{size}")
    public R<Object> findLotteryPage(@RequestBody BetVo vo, @PathVariable long current, @PathVariable long size) {
        LotteryBetEntity entity = MapperUtil.to(vo, LotteryBetEntity.class);
        BasePage basePage = lotteryBetService.findPage(entity, current, size, vo.getStart(), vo.getEnd());
        return R.builder().code(StatusCode.SUCCESS).data(basePage).build();
    }


}
