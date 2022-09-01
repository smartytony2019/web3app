package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.AgentCommissionEntity;
import com.xinbo.chainblock.entity.AgentCommissionRecordEntity;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.mapper.AgentCommissionMapper;
import com.xinbo.chainblock.mapper.AgentCommissionRecordMapper;
import com.xinbo.chainblock.mapper.MemberFlowMapper;
import com.xinbo.chainblock.mapper.MemberMapper;
import com.xinbo.chainblock.service.AgentCommissionRecordService;
import com.xinbo.chainblock.service.AgentCommissionService;
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
    private AgentCommissionRecordMapper agentCommissionRecordMapper;



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
