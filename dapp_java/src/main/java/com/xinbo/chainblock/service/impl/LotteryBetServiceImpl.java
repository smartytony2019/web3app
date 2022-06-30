package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.LotteryBetEntity;
import com.xinbo.chainblock.entity.LotteryPlayEntity;
import com.xinbo.chainblock.mapper.LotteryBetMapper;
import com.xinbo.chainblock.service.LotteryBetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class LotteryBetServiceImpl extends ServiceImpl<LotteryBetMapper, LotteryBetEntity> implements LotteryBetService {

    @Autowired
    private LotteryBetMapper lotteryBetMapper;


    @Override
    public boolean insert(LotteryBetEntity entity) {
        return lotteryBetMapper.insert(entity)> 0;
    }


    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<LotteryPlayEntity> createWrapper(LotteryPlayEntity entity) {
        LambdaQueryWrapper<LotteryPlayEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getNameCode())) {
            wrappers.eq(LotteryPlayEntity::getNameCode, entity.getNameCode());
        }
        return wrappers;
    }
}
