package com.xinbo.chainblock.service;


import com.xinbo.chainblock.entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface AgentCommissionService {
    boolean insertOrUpdate(List<AgentCommissionEntity> list);

    AgentCommissionEntity find(int uid, String date);
    AgentCommissionEntity find(AgentCommissionEntity entity);

    float findAvailableCommission(int uid);
    float findCommissionTotal(int uid);

    boolean applySubmit(int uid, AgentCommissionRecordEntity record, MemberEntity member, MemberFlowEntity memberFlow);

}
