package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.*;
import com.xinbo.chainblock.mapper.*;
import com.xinbo.chainblock.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tony
 * @date 8/15/22 4:07 下午
 * @desc 入帐业务处理
 */
@Service
public class TransferServiceImpl extends ServiceImpl<TransferMapper, TransferEntity> implements TransferService {

    @Autowired
    private TransferMapper transferMapper;


    @Override
    public boolean insert(TransferEntity entity) {
        return transferMapper.insert(entity) > 0;
    }

    @Override
    public List<TransferEntity> findUnconfirmed(long expired) {
        return transferMapper.findUnconfirmed(expired);
    }

    @Override
    public TransferEntity findByTransactionId(String transactionId) {
        return transferMapper.findByTransactionId(transactionId);
    }
}
