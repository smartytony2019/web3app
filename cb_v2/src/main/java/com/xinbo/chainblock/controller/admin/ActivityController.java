package com.xinbo.chainblock.controller.admin;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.EnumItemBo;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.PermissionDto;
import com.xinbo.chainblock.dto.UserDto;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleItemEntity;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.entity.admin.UserEntity;
import com.xinbo.chainblock.enums.*;
import com.xinbo.chainblock.service.ActivityService;
import com.xinbo.chainblock.service.UserService;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.ActivityRuleItemVo;
import com.xinbo.chainblock.vo.ActivityRuleVo;
import com.xinbo.chainblock.vo.ActivityVo;
import com.xinbo.chainblock.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController("adminActivityController")
@RequestMapping("/admin/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;


    @Operation(summary = "findPage", description = "会员列表")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody ActivityVo vo, @PathVariable long current, @PathVariable long size) {
        ActivityEntity entity = MapperUtil.to(vo, ActivityEntity.class);
        BasePageBo basePageBo = activityService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }


    @Operation(summary = "findCate", description = "类目")
    @PostMapping("findCate")
    public R<Object> findCate() {
        return R.builder().code(StatusCode.SUCCESS).data(ActivityCategoryEnum.toList()).build();
    }

    @Operation(summary = "findType", description = "类型")
    @PostMapping("findType")
    public R<Object> findType() {
        return R.builder().code(StatusCode.SUCCESS).data(ActivityTypeEnum.toList()).build();
    }

    @Operation(summary = "findLimitItem", description = "限制项")
    @PostMapping("findLimitItem")
    public R<Object> findLimitItem() {
        return R.builder().code(StatusCode.SUCCESS).data(ActivityRuleLimitItemEnum.toList()).build();
    }

    @Operation(summary = "findRuleCycle", description = "规则周期")
    @PostMapping("findRuleCycle")
    public R<Object> findRuleCycle() {
        return R.builder().code(StatusCode.SUCCESS).data(ActivityRuleCycleEnum.toList()).build();
    }

    @Operation(summary = "findReceiveMode", description = "领取方式")
    @PostMapping("findReceiveMode")
    public R<Object> findReceiveMode() {
        return R.builder().code(StatusCode.SUCCESS).data(ActivityReceiveModeEnum.toList()).build();
    }

    @Operation(summary = "findCalcMode", description = "计算方式")
    @PostMapping("findCalcMode")
    public R<Object> findCalcMode() {
        return R.builder().code(StatusCode.SUCCESS).data(ActivityCalcModeEnum.toList()).build();
    }

    @Operation(summary = "findRuleItemType", description = "规则项类型")
    @PostMapping("findRuleItemType")
    public R<Object> findRuleItemType() {
        return R.builder().code(StatusCode.SUCCESS).data(ActivityRuleItemTypeEnum.toList()).build();
    }


    @Operation(summary = "create", description = "创建")
    @PostMapping("create")
    public R<Object> create(@RequestBody ActivityVo vo) {
        System.out.println(vo);

        EnumItemBo cateEnum = ActivityCategoryEnum.valueOf(vo.getCateCode());

        String sn = IdUtil.getSnowflake().nextIdStr();
        ActivityEntity entity = ActivityEntity.builder()
                .cateCode(cateEnum.getCode())
                .cateName(cateEnum.getName())
                .cateNameZh(cateEnum.getNameZh())
                .sn(sn)
                .title(vo.getTitle())
                .img(vo.getImg())
                .content(vo.getContent())
                .sorted(vo.getSorted())
                .type(vo.getType())
                .language(vo.getLanguage())
                .beginTime(vo.getBeginTime())
                .finishTime(vo.getFinishTime())
                .createTime(DateUtil.date())
                .isEnable(vo.getIsEnable())
                .build();
        System.out.println(entity);

        ActivityRuleEntity ruleEntity = ActivityRuleEntity.builder()
                .sn(sn)
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
        System.out.println(ruleEntity);

        List<ActivityRuleItemVo> items = vo.getItems();
        List<ActivityRuleItemEntity> itemEntities = new ArrayList<>();
        for (ActivityRuleItemVo item : items) {
            ActivityRuleItemEntity itemEntity = ActivityRuleItemEntity.builder()
                    .sn(sn)
                    .type(item.getType())
                    .min(item.getMin())
                    .max(item.getMax())
                    .ratio(item.getRatio())
                    .build();
            itemEntities.add(itemEntity);
        }

        boolean isSuccess = activityService.create(entity, ruleEntity, itemEntities);

        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }


}
