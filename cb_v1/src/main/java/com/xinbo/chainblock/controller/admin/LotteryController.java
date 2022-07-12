package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.dto.MemberDto;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.BetVo;
import com.xinbo.chainblock.vo.MemberVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author tony
 * @date 7/11/22 7:24 下午
 * @desc file desc
 */
@RestController("adminLotteryController")
@RequestMapping("/admin/lottery")
public class LotteryController {

    @Autowired
    private LotteryCategoryService lotteryCategoryService;

    @Autowired
    private LotteryGameService lotteryGameService;

    @Autowired
    private LotteryPlayService lotteryPlayService;

    @Autowired
    private LotteryPlayCodeService lotteryPlayCodeService;



    @JwtIgnore
    @Operation(summary = "findCategory", description = "类目列表")
    @PostMapping("findCategory")
    public R<Object> findCategory() {
        List<LotteryCategoryEntity> list = lotteryCategoryService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(list).build();
    }


    @JwtIgnore
    @Operation(summary = "findGame", description = "游戏列表")
    @PostMapping("findGame")
    public R<Object> findGame() {
        List<LotteryGameEntity> list = lotteryGameService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(list).build();
    }


    @JwtIgnore
    @Operation(summary = "findPlay", description = "游戏列表")
    @PostMapping("findPlay")
    public R<Object> findPlay() {
        List<LotteryPlayEntity> list = lotteryPlayService.findAll();
        return R.builder().code(StatusCode.SUCCESS).data(list).build();
    }

}
