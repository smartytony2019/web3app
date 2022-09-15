package com.xinbo.chainblock.controller.admin;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.EnumItemBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.ActivityDto;
import com.xinbo.chainblock.dto.ActivityRuleDto;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleItemEntity;
import com.xinbo.chainblock.enums.*;
import com.xinbo.chainblock.service.ActivityRuleService;
import com.xinbo.chainblock.service.ActivityService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.ActivityRuleItemVo;
import com.xinbo.chainblock.vo.ActivityRuleVo;
import com.xinbo.chainblock.vo.ActivityVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("adminActivityRuleController")
@RequestMapping("/admin/activity/rule")
public class ActivityRuleController {

    @Autowired
    private ActivityRuleService activityRuleService;


    @Operation(summary = "find", description = "查找单条")
    @PostMapping("find")
    public R<Object> find(@RequestBody ActivityRuleVo vo) {
        ActivityRuleEntity entity = MapperUtil.to(vo, ActivityRuleEntity.class);
        ActivityRuleEntity result = activityRuleService.find(entity);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(result, ActivityRuleDto.class)).build();
    }


    @Operation(summary = "find", description = "订单号查找")
    @PostMapping("find/{sn}")
    public R<Object> find(@PathVariable String sn) {
        ActivityRuleEntity result = activityRuleService.findBySn(sn);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(result, ActivityRuleDto.class)).build();
    }


    @Operation(summary = "findItems", description = "订单号查找")
    @PostMapping("findItems/{sn}")
    public R<Object> findItems(@PathVariable String sn) {
        List<ActivityRuleItemEntity> result = activityRuleService.findItemsBySn(sn);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.many(result, ActivityRuleItemVo.class)).build();
    }

    @Operation(summary = "operate", description = "创建")
    @PostMapping("operate")
    public R<Object> operate(@RequestBody ActivityRuleVo vo) {
        ActivityRuleEntity entity;
        List<ActivityRuleItemEntity> itemEntities = new ArrayList<>();
        if (vo.getType() == ActivityTypeEnum.REGISTER.getCode()) {
            entity = ActivityRuleEntity.builder()
                    .sn(vo.getSn())
                    .money(vo.getMoney())
                    .symbol(vo.getSymbol())
                    .build();
        } else {
            entity = ActivityRuleEntity.builder()
                    .id(vo.getId())
                    .sn(vo.getSn())
                    .cycle(vo.getCycle())
                    .days(vo.getDays())
                    .limitItem(vo.getLimitItem())
                    .limitLev(0)
                    .withdrawBetMul(vo.getWithdrawBetMul())
                    .jackpotBetMul(vo.getJackpotBetMul())
                    .calcMode(vo.getCalcMode())
                    .receiveMode(vo.getReceiveMode())
                    .money(vo.getMoney())
                    .symbol(vo.getSymbol())
                    .build();

            List<ActivityRuleItemVo> items = vo.getItems();
            for (ActivityRuleItemVo item : items) {
                ActivityRuleItemEntity itemEntity = ActivityRuleItemEntity.builder()
                        .sn(vo.getSn())
                        .type(item.getType())
                        .min(item.getMin())
                        .max(item.getMax())
                        .ratio(item.getRatio())
                        .build();
                itemEntities.add(itemEntity);
            }
        }

        boolean isSuccess = activityRuleService.operate(entity, itemEntities);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

}
