package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.mapper.AgentMapper;
import com.xinbo.chainblock.service.AgentService;
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
public class AgentServiceImpl extends ServiceImpl<AgentMapper, AgentEntity> implements AgentService {

    @Autowired
    private AgentMapper agentMapper;

    @Override
    public boolean insert(AgentEntity entity) {
        return agentMapper.insert(entity) > 0;
    }

    @Override
    public List<AgentEntity> findAll(int skip, int size) {
        return agentMapper.findAll(skip, size);
    }

    @Override
    public boolean setChild(int id, String childStr) {
        return agentMapper.setChild(id, childStr) > 0;
    }

    @Override
    public AgentEntity findByUid(int id) {
        return agentMapper.findByUid(id);
    }

    @Override
    public List<AgentEntity> direct(int uid) {
        return agentMapper.directly(uid);
    }



    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<AgentEntity> createWrapper(AgentEntity entity) {
        LambdaQueryWrapper<AgentEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        return wrappers;
    }
}
