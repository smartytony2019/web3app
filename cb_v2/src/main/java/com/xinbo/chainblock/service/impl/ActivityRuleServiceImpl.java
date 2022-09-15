package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.ActivityDto;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleItemEntity;
import com.xinbo.chainblock.mapper.ActivityMapper;
import com.xinbo.chainblock.mapper.ActivityRuleItemMapper;
import com.xinbo.chainblock.mapper.ActivityRuleMapper;
import com.xinbo.chainblock.service.ActivityRuleService;
import com.xinbo.chainblock.service.ActivityService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc 活动规则
 */
@Service
public class ActivityRuleServiceImpl extends ServiceImpl<ActivityRuleMapper, ActivityRuleEntity> implements ActivityRuleService {

    @Autowired
    private ActivityRuleMapper activityRuleMapper;
    @Autowired
    private ActivityRuleItemMapper activityRuleItemMapper;


    @Override
    public ActivityRuleEntity findBySn(String sn) {
        return activityRuleMapper.findBySn(sn);
    }

    @Override
    public List<ActivityRuleItemEntity> findItemsBySn(String sn) {
        return activityRuleItemMapper.findBySn(sn);
    }

    @Override
    public ActivityRuleEntity find(ActivityRuleEntity entity) {
        return activityRuleMapper.selectOne(this.createWrapper(entity));
    }

    @Transactional
    @Override
    public boolean operate(ActivityRuleEntity entity, List<ActivityRuleItemEntity> itemEntities) {
        int rows = 0;
        if (ObjectUtils.isEmpty(entity.getId()) || entity.getId() <= 0) {
            rows = activityRuleMapper.insert(entity);
        } else {
            rows = activityRuleMapper.updateById(entity);
        }
        if (rows <= 0) {
            return false;
        }

        activityRuleItemMapper.deleteBySn(entity.getSn());
        for (ActivityRuleItemEntity item : itemEntities) {
            if(ObjectUtils.isEmpty(item.getMin()) || ObjectUtils.isEmpty(item.getMax())) {
                continue;
            }
            rows = activityRuleItemMapper.insert(item);
            if (rows <= 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<ActivityRuleEntity> createWrapper(ActivityRuleEntity entity) {
        LambdaQueryWrapper<ActivityRuleEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }

        if (!StringUtils.isEmpty(entity.getId()) && entity.getId() > 0) {
            wrappers.eq(ActivityRuleEntity::getId, entity.getId());
        }

        if (!StringUtils.isEmpty(entity.getSn())) {
            wrappers.eq(ActivityRuleEntity::getSn, entity.getSn());
        }

        return wrappers;
    }
}
