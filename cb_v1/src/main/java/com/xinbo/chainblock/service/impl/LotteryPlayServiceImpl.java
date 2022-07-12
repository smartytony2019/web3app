package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.LotteryPlayEntity;
import com.xinbo.chainblock.mapper.LotteryPlayMapper;
import com.xinbo.chainblock.service.LotteryPlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class LotteryPlayServiceImpl extends ServiceImpl<LotteryPlayMapper, LotteryPlayEntity> implements LotteryPlayService {

    @Autowired
    private LotteryPlayMapper lotteryPlayMapper;


    @Override
    public LotteryPlayEntity findById(int id) {
        return lotteryPlayMapper.selectById(id);
    }

    @Override
    public List<LotteryPlayEntity> findAll() {
        return lotteryPlayMapper.selectList(this.createWrapper(LotteryPlayEntity.builder().build()));
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
