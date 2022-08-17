package com.xinbo.chainblock.service.impl;

import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.mapper.*;
import com.xinbo.chainblock.service.CommonService;
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
public class CommonServiceImpl implements CommonService {

    @Autowired
    private FinanceMapper financeMapper;

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Autowired
    private MemberFlowMapper memberFlowMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private TransferMapper transferMapper;


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

    @Transactional
    @Override
    public boolean transfer(MemberEntity member, MemberFlowEntity flow) {
        int rows = memberMapper.increment(member);
        if (rows < 0) {
            return false;
        }

        rows = memberFlowMapper.insert(flow);
        if (rows < 0) {
            return false;
        }

        return true;
    }


}
