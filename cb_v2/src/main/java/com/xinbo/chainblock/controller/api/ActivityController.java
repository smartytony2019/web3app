package com.xinbo.chainblock.controller.api;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.DateRangeBo;
import com.xinbo.chainblock.bo.EnumItemBo;
import com.xinbo.chainblock.consts.ActivityConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
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

import java.util.ArrayList;
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
    private MemberService memberService;

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
            ActivityEntity activityEntity = activityService.findById(vo.getId());

            // 规则
            ActivityRuleEntity ruleEntity = activityRuleService.findByActivityId(activityEntity.getId());

            // 规则项
            List<ActivityRuleItemEntity> ruleItemEntityList = activityRuleItemService.findByRuleId(ruleEntity.getId());

            // 会员
            MemberEntity memberEntity = memberService.findById(uid);

            //*************************************** 活动条件 ******************************************
            int calcMode = ruleEntity.getCalcMode();
            int cycle = ruleEntity.getCycle();
            DateRangeBo dateRangeBo = DateRangeBo.builder().build();
            ActivityRecordEntity recordEntity = activityRecordService.find(vo.getId(), uid);


            dateRangeBo.setStartTimeStr("20220101");
            dateRangeBo.setEndTimeStr(DateUtil.yesterday().toString("yyyyMMdd"));
            if (!ObjectUtils.isEmpty(recordEntity) && recordEntity.getId() > 0) {
                // 判断今天是否领取一个
                String startTimeStr = DateUtil.date(recordEntity.getCreateTime()).toString("yyyyMMdd");
//                String todayStr = DateUtil.date(new Date()).toString("yyyyMMdd");
//                if (startTimeStr.equals(todayStr)) {
//                    throw new BusinessException(1, "已领取过");
//                }

                dateRangeBo.setStartTimeStr(startTimeStr);
            }

            // 一次
            if (cycle == ActivityRuleCycleEnum.ONE_TIME.getCode()) {
                if (!ObjectUtils.isEmpty(recordEntity) && recordEntity.getId() > 0) {
                    throw new BusinessException(1, "已领取过");
                }
            }


            // 不限次数
            if (cycle == ActivityRuleCycleEnum.UNLIMITED.getCode()) {
            }

            // 一天一次
            if (cycle == ActivityRuleCycleEnum.ONE_TIME_DAY.getCode()) {
                if (!ObjectUtils.isEmpty(recordEntity) && recordEntity.getId() > 0) {
                    String s1 = DateUtil.date(recordEntity.getCreateTime()).toString("yyyyMMdd");
                    String s2 = DateUtil.yesterday().toString("yyyyMMdd");
                    if (s1.equals(s2)) {
                        throw new BusinessException(1, "已领取过");
                    }
                }
            }

            // 一周一次
            if (cycle == ActivityRuleCycleEnum.ONE_TIME_WEEK.getCode()) {
                if (!ObjectUtils.isEmpty(recordEntity) && recordEntity.getId() > 0) {
                    int w1 = DateUtil.weekOfYear(recordEntity.getCreateTime());
                    int w2 = DateUtil.weekOfYear(new Date());
                    if (w1 == w2) {
                        throw new BusinessException(1, "已领取过");
                    }
                }
            }

            // 一月一次
            if (cycle == ActivityRuleCycleEnum.ONE_TIME_MONTH.getCode()) {
                int m1 = DateUtil.dayOfMonth(recordEntity.getCreateTime());
                int m2 = DateUtil.dayOfMonth(new Date());
                if (m1 == m2) {
                    throw new BusinessException(1, "已领取过");
                }
            }

            // 自定义天数
            if (cycle == ActivityRuleCycleEnum.CUSTOM.getCode()) {
                int days = ruleEntity.getDays();
                int b = (int)DateUtil.between(recordEntity.getCreateTime(), DateUtil.date(), DateUnit.DAY);
                if(b < days) {
                    throw new BusinessException(1, "已领取过");
                }
            }


            List<ActivityRecordEntity> recordEntityList = new ArrayList<>();
            // 充值
            if (ruleEntity.getLimitItem() == ActivityRuleLimitItemEnum.RECHARGE.getCode()) {
                List<StatisticsEntity> statisticsEntityList = statisticsService.findList(dateRangeBo, uid);

                for (StatisticsEntity se : statisticsEntityList) {
                    float ratio = 0;
                    for (ActivityRuleItemEntity itemEntity : ruleItemEntityList) {
                        if (se.getRechargeTrc20Amount() > itemEntity.getMin() && se.getRechargeTrc20Amount() <= itemEntity.getMax()) {
                            ratio = itemEntity.getRatio();
                        }
                    }

                    if (ratio == 0) {
                        continue;
                    }

                    float money = 0F;
                    if (calcMode == 1) {
                        money = ratio;
                    }

                    if (calcMode == 2) {
                        money = ratio * se.getRechargeTrc20Amount();
                    }

                    if (money <= 0) {
                        throw new BusinessException(1, "提交失败");
                    }

                    ActivityRecordEntity re = ActivityRecordEntity.builder()
                            .activityId(activityEntity.getId())
                            .activityName(activityEntity.getName())
                            .uid(memberEntity.getId())
                            .username(memberEntity.getUsername())
                            .money(money)
                            .symbol(ruleEntity.getSymbol())
                            .status(ActivityConst.UNHANDLED_RECORD)
                            .createTime(new Date())
                            .remark(se.getDate())
                            .build();
                    recordEntityList.add(re);
                }
            }

            // 首充
            if (ruleEntity.getLimitItem() == ActivityRuleLimitItemEnum.FIRST_RECHARGE.getCode()) {
            }

            // 打码
            if (ruleEntity.getLimitItem() == ActivityRuleLimitItemEnum.BET.getCode()) {
                List<StatisticsEntity> statisticsEntityList = statisticsService.findList(dateRangeBo, uid);
                for (StatisticsEntity se : statisticsEntityList) {
                    float ratio = 0;
                    for (ActivityRuleItemEntity itemEntity : ruleItemEntityList) {
                        if (se.getBetAmount() > itemEntity.getMin() && se.getBetAmount() <= itemEntity.getMax()) {
                            ratio = itemEntity.getRatio();
                            break;
                        }
                    }

                    if (ratio == 0) {
                        continue;
                    }

                    float money = 0F;
                    if (calcMode == 1) {
                        money = ratio;
                    }

                    if (calcMode == 2) {
                        money = ratio * se.getBetAmount();
                    }

                    if (money <= 0) {
                        throw new BusinessException(1, "提交失败");
                    }

                    ActivityRecordEntity re = ActivityRecordEntity.builder()
                            .activityId(activityEntity.getId())
                            .activityName(activityEntity.getName())
                            .uid(memberEntity.getId())
                            .username(memberEntity.getUsername())
                            .money(money)
                            .symbol(ruleEntity.getSymbol())
                            .status(ActivityConst.UNHANDLED_RECORD)
                            .createTime(new Date())
                            .remark(se.getDate())
                            .build();
                    recordEntityList.add(re);
                }
            }

            // 打码次数
            if (ruleEntity.getLimitItem() == ActivityRuleLimitItemEnum.BET_COUNT.getCode()) {
                List<StatisticsEntity> statisticsEntityList = statisticsService.findList(dateRangeBo, uid);
                for (StatisticsEntity se : statisticsEntityList) {
                    float ratio = 0;
                    for (ActivityRuleItemEntity itemEntity : ruleItemEntityList) {
                        if (se.getBetCount() > itemEntity.getMin() && se.getBetCount() <= itemEntity.getMax()) {
                            ratio = itemEntity.getRatio();
                            break;
                        }
                    }

                    if (ratio == 0) {
                        continue;
                    }

                    float money = ratio;

                    if (money <= 0) {
                        throw new BusinessException(1, "提交失败");
                    }

                    ActivityRecordEntity re = ActivityRecordEntity.builder()
                            .activityId(activityEntity.getId())
                            .activityName(activityEntity.getName())
                            .uid(memberEntity.getId())
                            .username(memberEntity.getUsername())
                            .money(money)
                            .symbol(ruleEntity.getSymbol())
                            .status(ActivityConst.UNHANDLED_RECORD)
                            .createTime(new Date())
                            .remark(se.getDate())
                            .build();
                    recordEntityList.add(re);
                }
            }

            // 注册
            if (ruleEntity.getLimitItem() == ActivityRuleLimitItemEnum.REGISTER.getCode()) {
            }

            if (recordEntityList.size() <= 0) {
                throw new BusinessException(1, "不符合");
            }

            boolean isSuccess = activityRecordService.batchInsert(recordEntityList);
            System.out.println(isSuccess);
        } catch (BusinessException ex) {
            System.out.println(ex.getMsg());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return R.builder().data(StatusCode.SUCCESS).build();
    }


}
