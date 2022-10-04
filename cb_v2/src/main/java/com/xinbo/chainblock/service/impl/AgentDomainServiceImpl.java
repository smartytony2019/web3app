package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.AgentDomainEntity;
import com.xinbo.chainblock.entity.AgentRebateEntity;
import com.xinbo.chainblock.mapper.AgentDomainMapper;
import com.xinbo.chainblock.mapper.AgentRebateMapper;
import com.xinbo.chainblock.service.AgentDomainService;
import com.xinbo.chainblock.service.AgentRebateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;


/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class AgentDomainServiceImpl extends ServiceImpl<AgentDomainMapper, AgentDomainEntity> implements AgentDomainService {

    @Autowired
    private AgentDomainMapper agentDomainMapper;


    @Override
    public List<AgentDomainEntity> findAll() {
        return agentDomainMapper.findAll();
    }

    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<AgentRebateEntity> createWrapper(AgentRebateEntity entity) {
        LambdaQueryWrapper<AgentRebateEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        return wrappers;
    }
}
