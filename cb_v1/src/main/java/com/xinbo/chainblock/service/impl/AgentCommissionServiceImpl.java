package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.AgentCommissionEntity;
import com.xinbo.chainblock.entity.AgentRebateEntity;
import com.xinbo.chainblock.entity.ExpectEntity;
import com.xinbo.chainblock.mapper.AgentCommissionMapper;
import com.xinbo.chainblock.mapper.AgentRebateMapper;
import com.xinbo.chainblock.service.AgentCommissionService;
import com.xinbo.chainblock.service.AgentRebateService;
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
public class AgentCommissionServiceImpl extends ServiceImpl<AgentCommissionMapper, AgentCommissionEntity> implements AgentCommissionService {

    @Autowired
    private AgentCommissionMapper agentCommissionMapper;

    public boolean insertOrUpdate(List<AgentCommissionEntity> list) {
        return agentCommissionMapper.insertOrUpdate(list) > 0;
    }

    /**
     * 创建查询条件
     *
     * @param entity  实体
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
        if (!StringUtils.isEmpty(entity.getUid()) && entity.getUid()>0) {
            wrappers.eq(AgentCommissionEntity::getUid, entity.getUid());
        }
        return wrappers;
    }
}
