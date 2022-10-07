package com.xinbo.chainblock.service;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.FinanceAuditEntity;
import com.xinbo.chainblock.entity.FinanceEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface FinanceAuditService {

    boolean insert(FinanceAuditEntity entity);

    FinanceAuditEntity findByUid(int uid);

    BasePageBo findPage(FinanceAuditEntity entity, long current, long size);
}
