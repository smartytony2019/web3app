package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.FinanceEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.mapper.*;
import com.xinbo.chainblock.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class FinanceServiceImpl extends ServiceImpl<FinanceMapper, FinanceEntity> implements FinanceService {

    @Autowired
    private FinanceMapper financeMapper;
    @Autowired
    private MemberFlowMapper memberFlowMapper;
    @Autowired
    private StatisticsMapper statisticsMapper;

    /**
     * 插入
     * @param entity
     * @return
     */
    @Override
    public boolean insert(FinanceEntity entity) {
        return financeMapper.insert(entity) > 0;
    }

    /**
     * 根据uid查找
     * @param uid
     * @return
     */
    @Override
    public FinanceEntity findByUid(int uid) {
        LambdaQueryWrapper<FinanceEntity> wrapper = this.createWrapper(FinanceEntity.builder().uid(uid).build());
        return financeMapper.selectOne(wrapper);
    }

    /**
     * 查找未入帐
     * @return
     */
    @Override
    public List<FinanceEntity> findUnaccounted() {
        return financeMapper.findUnaccounted();
    }

    @Transactional
    @Override
    public boolean account(List<FinanceEntity> financeList, List<MemberFlowEntity> flowList, List<StatisticsEntity> statisticsList) {
        int rows = financeMapper.batchInsert(financeList);
        if (rows < 0) {
            return false;
        }

        rows = memberFlowMapper.batchInsert(flowList);
        if (rows < 0) {
            return false;
        }

        rows = statisticsMapper.batchInsert(statisticsList);
        if (rows < 0) {
            return false;
        }

        return true;
    }


    /**
     * 批量插入
     * @param list
     * @return
     */
    @Override
    public int batchInsert(List<FinanceEntity> list) {
        return financeMapper.batchInsert(list);
    }


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<FinanceEntity> createWrapper(FinanceEntity entity) {
        LambdaQueryWrapper<FinanceEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUid())) {
            wrappers.eq(FinanceEntity::getUid, entity.getUid());
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(FinanceEntity::getUsername, entity.getUsername());
        }
        return wrappers;
    }


}
