package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.AgentCommissionDto;
import com.xinbo.chainblock.dto.AgentCommissionRecordDto;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.mapper.*;
import com.xinbo.chainblock.service.AgentCommissionRecordService;
import com.xinbo.chainblock.service.AgentCommissionService;
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
 * @desc file desc
 */
@Service
public class AgentCommissionRecordServiceImpl extends ServiceImpl<AgentCommissionRecordMapper, AgentCommissionRecordEntity> implements AgentCommissionRecordService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberFlowMapper memberFlowMapper;

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Autowired
    private AgentCommissionRecordMapper agentCommissionRecordMapper;


    @Override
    public AgentCommissionRecordEntity findById(int id) {
        return agentCommissionRecordMapper.selectById(id);
    }

    @Override
    public boolean handle(AgentCommissionRecordEntity entity, MemberEntity member, MemberFlowEntity memberFlow, StatisticsEntity statistics) {

        // 佣金记录表
        int rows = agentCommissionRecordMapper.updateById(entity);
        if (rows <= 0) {
            return false;
        }

        // 会员表
        rows = memberMapper.increment(member);
        if (rows <= 0) {
            return false;
        }

        // 会员流水表
        rows = memberFlowMapper.insert(memberFlow);
        if (rows <= 0) {
            return false;
        }

        // 统计表
        rows = statisticsMapper.insertOrUpdate(statistics);
        if (rows <= 0) {
            return false;
        }

        return true;
    }

    @Override
    public BasePageBo findPage(AgentCommissionRecordEntity entity, long current, long size) {
        Page<AgentCommissionRecordEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.desc("create_timestamp"));

        IPage<AgentCommissionRecordEntity> iPage = agentCommissionRecordMapper.selectPage(page, this.createWrapper(entity));
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), AgentCommissionRecordDto.class)).build();
    }


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<AgentCommissionRecordEntity> createWrapper(AgentCommissionRecordEntity entity) {
        LambdaQueryWrapper<AgentCommissionRecordEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(AgentCommissionRecordEntity::getUsername, entity.getUsername());
        }
        if (!StringUtils.isEmpty(entity.getUid()) && entity.getUid() > 0) {
            wrappers.eq(AgentCommissionRecordEntity::getUid, entity.getUid());
        }
        return wrappers;
    }
}
