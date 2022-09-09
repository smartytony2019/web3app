package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.DateRangeBo;
import com.xinbo.chainblock.dto.MemberFlowDto;
import com.xinbo.chainblock.dto.StatisticsDto;
import com.xinbo.chainblock.entity.AgentCommissionEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.mapper.StatisticsMapper;
import com.xinbo.chainblock.service.StatisticsService;
import com.xinbo.chainblock.utils.MapperUtil;
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

    @Override
    public BasePageBo findPage(DateRangeBo dateRangeBo, int uid, long current, long size) {
        Page<StatisticsEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.desc("date"));

        LambdaQueryWrapper<StatisticsEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StatisticsEntity::getUid, uid);
        wrapper.ge(StatisticsEntity::getDate, dateRangeBo.getStartTimeStr()).le(StatisticsEntity::getDate, dateRangeBo.getEndTimeStr());

        IPage<StatisticsEntity> iPage = statisticsMapper.selectPage(page, wrapper);
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), StatisticsDto.class)).build();
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
