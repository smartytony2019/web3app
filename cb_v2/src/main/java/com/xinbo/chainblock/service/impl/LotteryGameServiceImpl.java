package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.LotteryGameEntity;
import com.xinbo.chainblock.mapper.LotteryGameMapper;
import com.xinbo.chainblock.service.LotteryGameService;
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
public class LotteryGameServiceImpl extends ServiceImpl<LotteryGameMapper, LotteryGameEntity> implements LotteryGameService {

    @Autowired
    private LotteryGameMapper lotteryGameMapper;



    @Override
    public LotteryGameEntity findById(int id) {
        return lotteryGameMapper.selectById(id);
    }


    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<LotteryGameEntity> createWrapper(LotteryGameEntity entity) {
        LambdaQueryWrapper<LotteryGameEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getNameCode())) {
            wrappers.eq(LotteryGameEntity::getNameCode, entity.getNameCode());
        }
        return wrappers;
    }
}
