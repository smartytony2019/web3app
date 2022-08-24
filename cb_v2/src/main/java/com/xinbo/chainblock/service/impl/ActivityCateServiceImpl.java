package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.activity.ActivityCateEntity;
import com.xinbo.chainblock.mapper.ActivityCateMapper;
import com.xinbo.chainblock.service.ActivityCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc 活动类目
 */
@Service
public class ActivityCateServiceImpl extends ServiceImpl<ActivityCateMapper, ActivityCateEntity> implements ActivityCateService {

    @Autowired
    private ActivityCateMapper activityCateMapper;


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<ActivityCateEntity> createWrapper(ActivityCateEntity entity) {
        LambdaQueryWrapper<ActivityCateEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }

        if (!StringUtils.isEmpty(entity.getId()) && entity.getId() > 0) {
            wrappers.eq(ActivityCateEntity::getId, entity.getId());
        }

        return wrappers;
    }
}
