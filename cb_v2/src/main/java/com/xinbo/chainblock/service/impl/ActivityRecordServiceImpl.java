package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.ActivityDto;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
import com.xinbo.chainblock.entity.activity.ActivityRecordEntity;
import com.xinbo.chainblock.mapper.*;
import com.xinbo.chainblock.service.ActivityRecordService;
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
 * @desc 活动记录
 */
@Service
public class ActivityRecordServiceImpl extends ServiceImpl<ActivityRecordMapper, ActivityRecordEntity> implements ActivityRecordService {

    @Autowired
    private ActivityRecordMapper activityRecordMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberFlowMapper memberFlowMapper;
    @Autowired
    private StatisticsMapper statisticsMapper;


    @Override
    public ActivityRecordEntity find(int id) {
        return activityRecordMapper.selectById(id);
    }

    @Override
    public ActivityRecordEntity find(int activityId, int uid) {
        return activityRecordMapper.find(activityId, uid);
    }

    @Override
    public boolean batchInsert(List<ActivityRecordEntity> list) {
        return activityRecordMapper.batchInsert(list) > 0;
    }

    @Transactional
    @Override
    public boolean submit(List<ActivityRecordEntity> list, MemberEntity member, MemberFlowEntity memberFlow, StatisticsEntity statistics) {
        int rows = activityRecordMapper.batchInsert(list);
        if (rows <= 0) {
            return false;
        }

        // 加减会员金额
        if(!ObjectUtils.isEmpty(member)) {
            rows = memberMapper.increment(member);
            if (rows <= 0) {
                return false;
            }
        }

        // 添加会员流水
        if(!ObjectUtils.isEmpty(memberFlow)) {
            rows = memberFlowMapper.insert(memberFlow);
            if (rows <= 0) {
                return false;
            }
        }

        if(!ObjectUtils.isEmpty(statistics)) {
            rows = statisticsMapper.insertOrUpdate(statistics);
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
    private LambdaQueryWrapper<ActivityRecordEntity> createWrapper(ActivityRecordEntity entity) {
        LambdaQueryWrapper<ActivityRecordEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }

        if (!StringUtils.isEmpty(entity.getId()) && entity.getId() > 0) {
            wrappers.eq(ActivityRecordEntity::getId, entity.getId());
        }

        return wrappers;
    }
}
