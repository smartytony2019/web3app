package com.xinbo.chainblock.service;


import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.AgentCommissionRecordEntity;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface AgentCommissionRecordService {
    AgentCommissionRecordEntity findById(int id);

    boolean handle(AgentCommissionRecordEntity entity, MemberEntity member, MemberFlowEntity memberFlow, StatisticsEntity statistics);

    BasePageBo findPage(AgentCommissionRecordEntity entity, long current, long size);
}
