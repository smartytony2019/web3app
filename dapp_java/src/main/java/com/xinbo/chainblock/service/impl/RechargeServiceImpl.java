package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.RechargeEntity;
import com.xinbo.chainblock.entity.UserEntity;
import com.xinbo.chainblock.mapper.RechargeMapper;
import com.xinbo.chainblock.mapper.UserMapper;
import com.xinbo.chainblock.service.RechargeService;
import com.xinbo.chainblock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
//@Service
public class RechargeServiceImpl extends ServiceImpl<RechargeMapper, RechargeEntity> implements RechargeService {

    @Autowired
    private RechargeService rechargeService;


    public boolean create() {
        return true;
    }





}
