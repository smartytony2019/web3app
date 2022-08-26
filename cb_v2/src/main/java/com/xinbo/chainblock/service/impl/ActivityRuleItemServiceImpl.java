package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.activity.ActivityRuleEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleItemEntity;
import com.xinbo.chainblock.mapper.ActivityRuleItemMapper;
import com.xinbo.chainblock.mapper.ActivityRuleMapper;
import com.xinbo.chainblock.service.ActivityRuleItemService;
import com.xinbo.chainblock.service.ActivityRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc 活动规则项
 */
@Service
public class ActivityRuleItemServiceImpl extends ServiceImpl<ActivityRuleItemMapper, ActivityRuleItemEntity> implements ActivityRuleItemService {

    @Autowired
    private ActivityRuleItemMapper activityRuleItemMapper;


    @Override
    public List<ActivityRuleItemEntity> findBySn(String sn) {
        return activityRuleItemMapper.findBySn(sn);
    }

    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<ActivityRuleItemEntity> createWrapper(ActivityRuleItemEntity entity) {
        LambdaQueryWrapper<ActivityRuleItemEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }

        if (!StringUtils.isEmpty(entity.getId()) && entity.getId() > 0) {
            wrappers.eq(ActivityRuleItemEntity::getId, entity.getId());
        }

        return wrappers;
    }
}
