package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.service.HashBetService;
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
@RestController("AdminAgentController")
@RequestMapping("/admin/agent")
public class AgentController {

    @Autowired
    private HashBetService hashBetService;


    @JwtIgnore
    @Operation(summary = "findLotteryPage", description = "注单列表")
    @PostMapping("findLotteryPage/{current}/{size}")
    public R<Object> findLotteryPage(@RequestBody BetVo vo, @PathVariable long current, @PathVariable long size) {
        HashBetEntity entity = MapperUtil.to(vo, HashBetEntity.class);
        BasePageBo basePageBo = hashBetService.findPage(entity, current, size, null, null);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }


}
