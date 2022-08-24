package com.xinbo.chainblock.controller.api;

import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.DateRangeBo;
import com.xinbo.chainblock.bo.EnumItemBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.activity.ActivityRecordEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleItemEntity;
import com.xinbo.chainblock.enums.ActivityRuleCycleEnum;
import com.xinbo.chainblock.enums.ActivityRuleLimitItemEnum;
import com.xinbo.chainblock.exception.BusinessException;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.ActivityVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController("ApiActivityController")
@RequestMapping("api/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRecordService activityRecordService;

    @Autowired
    private ActivityRuleService activityRuleService;

    @Autowired
    private ActivityRuleItemService activityRuleItemService;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private StatisticsService statisticsService;


    @JwtIgnore
    @Operation(summary = "insert", description = "插入")
    @PostMapping("insert")
    public R<Object> insert() {
        boolean isSuccess = activityService.insert(ActivityEntity.builder().build());
        return isSuccess ? R.builder().data(StatusCode.SUCCESS).build() : R.builder().data(StatusCode.FAILURE).build();
    }


    @JwtIgnore
    @Operation(summary = "find", description = "插入")
    @PostMapping("find")
    public R<Object> find(@RequestBody ActivityVo vo) {
        ActivityEntity entity = MapperUtil.to(vo, ActivityEntity.class);
        entity = activityService.find(entity);
        return R.builder().data(StatusCode.SUCCESS).data(entity).build();
    }

    @JwtIgnore
    @Operation(summary = "findPage", description = "获取分页")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody ActivityVo vo, @PathVariable long current, @PathVariable long size) {
        ActivityEntity entity = MapperUtil.to(vo, ActivityEntity.class);
        BasePageBo basePageBo = activityService.findPage(entity, current, size);
        return R.builder().data(StatusCode.SUCCESS).data(basePageBo).build();
    }


    @JwtIgnore
    @Operation(summary = "submit", description = "获取分页")
    @PostMapping("submit")
    public R<Object> submit(@RequestBody ActivityVo vo) {
        try {

            int uid = 19;
            // 活动
            ActivityEntity entity = MapperUtil.to(vo, ActivityEntity.class);

            // 规则
            ActivityRuleEntity ruleEntity = activityRuleService.findByActivityId(entity.getId());

            // 规则项
            List<ActivityRuleItemEntity> ruleItemEntityList = activityRuleItemService.findByRuleId(ruleEntity.getId());


            //*************************************** 活动条件 ******************************************
            int calcMode = ruleEntity.getCalcMode();
            int cycle = ruleEntity.getCycle();
            DateRangeBo dateRangeBo = DateRangeBo.builder().build();
            ActivityRecordEntity recordEntity = activityRecordService.find(vo.getId(), uid);
/*
        // 一次
        if (cycle == ActivityRuleCycleEnum.ONE_TIME.getCode()) {

        }

        // 不限次数
        if (cycle == ActivityRuleCycleEnum.UNLIMITED.getCode()) {
            dateRangeBo.setStartTimestamp(0L);
            dateRangeBo.setEndTimestamp(System.currentTimeMillis());
            if (!ObjectUtils.isEmpty(recordEntity) && recordEntity.getId() > 0) {
                dateRangeBo.setStartTimestamp(recordEntity.getCreateTime().getTime() * 1000L);
            }
        }

        // 一天一次
        if (cycle == ActivityRuleCycleEnum.ONE_TIME_DAY.getCode()) {

        }

        // 一周一次
        if (cycle == ActivityRuleCycleEnum.ONE_TIME_WEEK.getCode()) {

        }

        // 一月一次
        if (cycle == ActivityRuleCycleEnum.ONE_TIME_MONTH.getCode()) {

        }

        // 自定义天数
        if (cycle == ActivityRuleCycleEnum.CUSTOM.getCode()) {

        }*/


//            dateRangeBo.setStartTime(0L);
            dateRangeBo.setEndTimestamp(System.currentTimeMillis());
            if (!ObjectUtils.isEmpty(recordEntity) && recordEntity.getId() > 0) {
                dateRangeBo.setStartTimestamp(recordEntity.getCreateTime().getTime());
            }


            // 充值
            if (ruleEntity.getLimitItem() == ActivityRuleLimitItemEnum.RECHARGE.getCode()) {
                List<StatisticsEntity> statisticsEntityList = statisticsService.findList(dateRangeBo, uid);

                float money = 0F;
                for (StatisticsEntity se : statisticsEntityList) {
                    int ratio = 0;
                    for (ActivityRuleItemEntity itemEntity : ruleItemEntityList) {
                        if (se.getRechargeTrc20Amount() > itemEntity.getMin() && se.getRechargeTrc20Amount() < itemEntity.getMax()) {
                            ratio = itemEntity.getRatio();
                        }
                    }

                    if (ratio == 0) {
                        continue;
                    }

                    if (ruleEntity.getCalcMode() == 1) {
                        money = ratio * 1F;
                    }

                    if (ruleEntity.getCalcMode() == 2) {
                        money = ratio * se.getRechargeTrc20Amount();
                    }
                }

                if (money <= 0) {
                    throw new BusinessException(1, "提交失败");
                }
            }

            // 首充
            if (ruleEntity.getLimitItem() == ActivityRuleLimitItemEnum.FIRST_RECHARGE.getCode()) {
            }

            // 打码
            if (ruleEntity.getLimitItem() == ActivityRuleLimitItemEnum.BET.getCode()) {
                statisticsService.findList(dateRangeBo, uid);
            }

            // 打码次数
            if (ruleEntity.getLimitItem() == ActivityRuleLimitItemEnum.BET_COUNT.getCode()) {
                statisticsService.findList(dateRangeBo, uid);
            }

            // 注册
            if (ruleEntity.getLimitItem() == ActivityRuleLimitItemEnum.REGISTER.getCode()) {

            }

        } catch (BusinessException ex) {

            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return R.builder().data(StatusCode.SUCCESS).build();
    }


}
