package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.DateRangeBo;
import com.xinbo.chainblock.dto.AgentCommissionDto;
import com.xinbo.chainblock.dto.StatisticsDto;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.mapper.AgentCommissionMapper;
import com.xinbo.chainblock.mapper.AgentCommissionRecordMapper;
import com.xinbo.chainblock.mapper.MemberFlowMapper;
import com.xinbo.chainblock.mapper.MemberMapper;
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
public class AgentCommissionServiceImpl extends ServiceImpl<AgentCommissionMapper, AgentCommissionEntity> implements AgentCommissionService {

    @Autowired
    private AgentCommissionMapper agentCommissionMapper;
    @Autowired
    private AgentCommissionRecordMapper agentCommissionRecordMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberFlowMapper memberFlowMapper;

    public boolean insertOrUpdate(List<AgentCommissionEntity> list) {
        return agentCommissionMapper.insertOrUpdate(list) > 0;
    }

    @Override
    public AgentCommissionEntity find(int uid, String date) {
        return agentCommissionMapper.find(uid, date);
    }

    @Override
    public AgentCommissionEntity find(AgentCommissionEntity entity) {
        return agentCommissionMapper.selectOne(this.createWrapper(entity));
    }

    @Override
    public float findAvailableCommission(int uid) {
        return agentCommissionMapper.findAvailableCommission(uid);
    }

    @Override
    public float findCommissionTotal(int uid) {
        return agentCommissionMapper.findCommissionTotal(uid);
    }

    @Transactional
    @Override
    public boolean applySubmit(AgentCommissionRecordEntity entity) {
        // 代理佣金表
        int rows = agentCommissionMapper.accounted(entity.getUid());
        if (rows <= 0) {
            return false;
        }

        rows = agentCommissionRecordMapper.insert(entity);
        if (rows <= 0) {
            return false;
        }

        return true;
    }

    @Override
    public BasePageBo findPage(DateRangeBo dateRangeBo, int uid, long current, long size) {
        Page<AgentCommissionEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.desc("date"));

        LambdaQueryWrapper<AgentCommissionEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AgentCommissionEntity::getUid, uid);
        wrapper.ge(AgentCommissionEntity::getDate, dateRangeBo.getStartTimeStr()).le(AgentCommissionEntity::getDate, dateRangeBo.getEndTimeStr());

        IPage<AgentCommissionEntity> iPage = agentCommissionMapper.selectPage(page, wrapper);
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), AgentCommissionDto.class)).build();
    }

    @Override
    public AgentCommissionEntity findTotal(DateRangeBo dateRangeBo, int uid) {
        return agentCommissionMapper.findTotal(dateRangeBo, uid);
    }


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<AgentCommissionEntity> createWrapper(AgentCommissionEntity entity) {
        LambdaQueryWrapper<AgentCommissionEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(AgentCommissionEntity::getUsername, entity.getUsername());
        }
        if (!StringUtils.isEmpty(entity.getUid()) && entity.getUid() > 0) {
            wrappers.eq(AgentCommissionEntity::getUid, entity.getUid());
        }
        if (!StringUtils.isEmpty(entity.getDate())) {
            wrappers.eq(AgentCommissionEntity::getDate, entity.getDate());
        }
        return wrappers;
    }
}
