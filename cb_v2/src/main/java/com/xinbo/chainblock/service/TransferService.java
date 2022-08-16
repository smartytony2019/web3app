package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.FinanceEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.entity.TransferEntity;

import java.util.List;

/**
 * @author tony
 * @date 8/15/22 4:06 下午
 * @desc file desc
 */
public interface TransferService {

    boolean insert(TransferEntity entity);

    List<TransferEntity> findUnconfirmed(long expired);

    TransferEntity findByTransactionId(String transactionId);
}
