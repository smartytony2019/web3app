package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.ActivityDto;
import com.xinbo.chainblock.dto.HashBetDto;
import com.xinbo.chainblock.entity.FinanceEntity;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleEntity;
import com.xinbo.chainblock.entity.activity.ActivityRuleItemEntity;
import com.xinbo.chainblock.mapper.ActivityMapper;
import com.xinbo.chainblock.mapper.ActivityRuleItemMapper;
import com.xinbo.chainblock.mapper.ActivityRuleMapper;
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
 * @desc 活动
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityEntity> implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityRuleMapper activityRuleMapper;
    @Autowired
    private ActivityRuleItemMapper activityRuleItemMapper;


    @Override
    public boolean insert(ActivityEntity entity) {
        return activityMapper.insert(entity) > 0;
    }

    @Override
    public List<ActivityEntity> findAll(int skip, int size) {
        return activityMapper.findAll(skip, size);
    }

    @Override
    public ActivityEntity findById(int id) {
        return activityMapper.selectById(id);
    }

    @Override
    public ActivityEntity find(ActivityEntity entity) {
        return activityMapper.selectOne(this.createWrapper(entity));
    }


    @Override
    public BasePageBo findPage(ActivityEntity entity, long current, long size) {
        Page<ActivityEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("sorted"));
        IPage<ActivityEntity> iPage = activityMapper.selectPage(page, this.createWrapper(entity));
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), ActivityDto.class)).build();
    }

    @Override
    public ActivityEntity findByType(int type) {
        return activityMapper.findByType(type);
    }

    @Override
    public boolean create(ActivityEntity entity) {
        return activityMapper.insert(entity) > 0;
    }

    @Override
    public boolean update(ActivityEntity entity) {
        return activityMapper.updateById(entity) > 0;
    }

    @Transactional
    @Override
    public boolean delete(String sn) {
        activityMapper.deleteBySn(sn);
        activityRuleMapper.deleteBySn(sn);
        activityRuleItemMapper.deleteBySn(sn);
        return true;
    }


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<ActivityEntity> createWrapper(ActivityEntity entity) {
        LambdaQueryWrapper<ActivityEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }

        if (!StringUtils.isEmpty(entity.getId()) && entity.getId() > 0) {
            wrappers.eq(ActivityEntity::getId, entity.getId());
        }

        if (!StringUtils.isEmpty(entity.getCateCode()) && entity.getCateCode() > 0) {
            wrappers.eq(ActivityEntity::getCateCode, entity.getCateCode());
        }

        if (!StringUtils.isEmpty(entity.getType()) && entity.getType() > 0) {
            wrappers.eq(ActivityEntity::getType, entity.getType());
        }

        return wrappers;
    }
}
