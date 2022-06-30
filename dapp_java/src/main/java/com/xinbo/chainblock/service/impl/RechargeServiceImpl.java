package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.modal.Do.RechargeDo;
import com.xinbo.chainblock.mapper.RechargeMapper;
import com.xinbo.chainblock.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class RechargeServiceImpl extends ServiceImpl<RechargeMapper, RechargeDo> implements RechargeService {


    @Autowired
    private RechargeMapper rechargeMapper;

    public boolean save(RechargeDo entity) {
        int insert = rechargeMapper.insert(entity);
        return insert > 0;
    }





}
