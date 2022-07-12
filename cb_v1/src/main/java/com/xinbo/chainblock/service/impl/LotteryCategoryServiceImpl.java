package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.LotteryCategoryEntity;
import com.xinbo.chainblock.entity.LotteryGameEntity;
import com.xinbo.chainblock.mapper.LotteryCategoryMapper;
import com.xinbo.chainblock.service.LotteryCategoryService;
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
public class LotteryCategoryServiceImpl extends ServiceImpl<LotteryCategoryMapper, LotteryCategoryEntity> implements LotteryCategoryService {

    @Autowired
    private LotteryCategoryMapper lotteryCategoryMapper;



    @Override
    public LotteryCategoryEntity findById(int id) {
        return lotteryCategoryMapper.selectById(id);
    }

    @Override
    public List<LotteryCategoryEntity> findAll() {
        return lotteryCategoryMapper.selectList(this.createWrapper(LotteryCategoryEntity.builder().build()));
    }


    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<LotteryCategoryEntity> createWrapper(LotteryCategoryEntity entity) {
        LambdaQueryWrapper<LotteryCategoryEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getNameCode())) {
            wrappers.eq(LotteryCategoryEntity::getNameCode, entity.getNameCode());
        }
        return wrappers;
    }
}
