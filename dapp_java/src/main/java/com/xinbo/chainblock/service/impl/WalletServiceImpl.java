package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.RechargeEntity;
import com.xinbo.chainblock.entity.WalletEntity;
import com.xinbo.chainblock.mapper.RechargeMapper;
import com.xinbo.chainblock.mapper.WalletMapper;
import com.xinbo.chainblock.service.RechargeService;
import com.xinbo.chainblock.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class WalletServiceImpl extends ServiceImpl<WalletMapper, WalletEntity> implements WalletService {

    @Autowired
    private WalletService walletService;


    public boolean create() {
        return true;
    }





}
