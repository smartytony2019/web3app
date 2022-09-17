package com.xinbo.chainblock.controller.admin;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.EnumItemBo;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.ActivityDto;
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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController("adminActivityController")
@RequestMapping("/admin/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Operation(summary = "find", description = "查找单条")
    @PostMapping("find")
    public R<Object> find(@RequestBody ActivityVo vo) {
        ActivityEntity entity = MapperUtil.to(vo, ActivityEntity.class);
        ActivityEntity result = activityService.find(entity);
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(result, ActivityDto.class)).build();
    }

    @Operation(summary = "findPage", description = "查找分页")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody ActivityVo vo, @PathVariable long current, @PathVariable long size) {
        ActivityEntity entity = MapperUtil.to(vo, ActivityEntity.class);
        BasePageBo basePageBo = activityService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }

    @Operation(summary = "operate", description = "操作(添加|修改)")
    @PostMapping("operate")
    public R<Object> operate(@RequestBody ActivityVo vo) {
        EnumItemBo cateEnum = ActivityCategoryEnum.valueOf(vo.getCateCode());
        ActivityEntity entity = ActivityEntity.builder()
                .cateCode(cateEnum.getCode())
                .cateName(cateEnum.getName())
                .cateNameZh(cateEnum.getNameZh())
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

        boolean isSuccess;
        if(ObjectUtils.isEmpty(vo.getId()) || vo.getId()<=0) {
            entity.setSn(IdUtil.getSnowflake().nextIdStr());
            isSuccess = activityService.create(entity);
        } else {
            entity.setId(vo.getId());
            isSuccess = activityService.update(entity);
        }
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }


    @Operation(summary = "delete", description = "删除")
    @PostMapping("delete/{sn}")
    public R<Object> delete(@PathVariable String sn) {
        boolean isSuccess = activityService.delete(sn);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
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





}
