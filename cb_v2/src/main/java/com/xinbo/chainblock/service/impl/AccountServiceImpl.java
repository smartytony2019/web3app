package com.xinbo.chainblock.service.impl;

import com.xinbo.chainblock.entity.FinanceEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.mapper.FinanceMapper;
import com.xinbo.chainblock.mapper.MemberFlowMapper;
import com.xinbo.chainblock.mapper.StatisticsMapper;
import com.xinbo.chainblock.service.AccountService;
import com.xinbo.chainblock.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tony
 * @date 8/15/22 4:07 下午
 * @desc 入帐业务处理
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private FinanceMapper financeMapper;

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Autowired
    private MemberFlowMapper memberFlowMapper;


    /**
     * 资金变更入帐
     *
     * @param financeList
     * @param flowList
     * @param statisticsList
     * @return
     */
    @Transactional
    @Override
    public boolean financeAccount(List<FinanceEntity> financeList, List<MemberFlowEntity> flowList, List<StatisticsEntity> statisticsList) {
        int rows = financeMapper.batchInsert(financeList);
        if (rows < 0) {
            return false;
        }

        rows = memberFlowMapper.batchInsert(flowList);
        if (rows < 0) {
            return false;
        }

        rows = statisticsMapper.batchInsert(statisticsList);
        if (rows < 0) {
            return false;
        }

        return true;
    }
}
