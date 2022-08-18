package com.xinbo.chainblock.service;
import com.xinbo.chainblock.entity.FinanceEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.entity.WalletEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface FinanceService {

    boolean insert(FinanceEntity entity);

    FinanceEntity findByUid(int uid);

    List<FinanceEntity> findUnaccounted();


    /**
     * 入帐
     * @param financeList
     * @param flowList
     * @param statisticsList
     * @return
     */
    boolean account(List<FinanceEntity> financeList, List<MemberFlowEntity> flowList, List<StatisticsEntity> statisticsList);

    int batchInsert(List<FinanceEntity> list);
}
