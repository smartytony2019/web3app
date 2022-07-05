package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.AgentRebateEntity;
import com.xinbo.chainblock.entity.ExpectEntity;
import com.xinbo.chainblock.mapper.AgentRebateMapper;
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
public class AgentRebateServiceImpl extends ServiceImpl<AgentRebateMapper, AgentRebateEntity> implements AgentRebateService {

    @Autowired
    private AgentRebateMapper agentRebateMapper;


    @Override
    public List<AgentRebateEntity> findAll() {
        return agentRebateMapper.findAll();
    }

    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<ExpectEntity> createWrapper(ExpectEntity entity) {
        LambdaQueryWrapper<ExpectEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getGameId())) {
            wrappers.eq(ExpectEntity::getGameId, entity.getGameId());
        }
        if (!StringUtils.isEmpty(entity.getNum())) {
            wrappers.eq(ExpectEntity::getNum, entity.getNum());
        }
        return wrappers;
    }
}
