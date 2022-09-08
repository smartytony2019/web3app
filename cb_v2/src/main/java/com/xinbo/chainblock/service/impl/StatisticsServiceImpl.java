package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.DateRangeBo;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.mapper.StatisticsMapper;
import com.xinbo.chainblock.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, StatisticsEntity> implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;


    @Override
    public List<StatisticsEntity> findByUidStr(String date, List<Integer> childList) {
        return statisticsMapper.findByUidStr(date, childList);
    }

    @Override
    public StatisticsEntity findByUid(String date, int childUid) {
        return statisticsMapper.findByUid(date, childUid);
    }

    @Override
    public List<StatisticsEntity> findList(DateRangeBo dateRangeBo, int uid) {
        return statisticsMapper.findList(dateRangeBo, uid);
    }

    @Override
    public List<StatisticsEntity> findByDate(String date) {
        return statisticsMapper.findByDate(date);
    }

    @Override
    public StatisticsEntity findTotal(DateRangeBo dateRangeBo, int uid) {
        return statisticsMapper.findTotal(dateRangeBo, uid);
    }


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<StatisticsEntity> createWrapper(StatisticsEntity entity) {
        LambdaQueryWrapper<StatisticsEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(StatisticsEntity::getUsername, entity.getUsername());
        }
        if (!StringUtils.isEmpty(entity.getUid()) && entity.getUid() > 0) {
            wrappers.eq(StatisticsEntity::getUid, entity.getUid());
        }
        return wrappers;
    }
}
