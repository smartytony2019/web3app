package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.GameEntity;
import com.xinbo.chainblock.mapper.HashGameMapper;
import com.xinbo.chainblock.service.HashGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class HashGameServiceImpl extends ServiceImpl<HashGameMapper, GameEntity> implements HashGameService {

    @Autowired
    private HashGameMapper hashGameMapper;



    @Override
    public GameEntity findById(int id) {
        return hashGameMapper.selectById(id);
    }

    @Override
    public List<GameEntity> findAll() {
        return hashGameMapper.selectList(this.createWrapper(GameEntity.builder().build()));
    }


    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<GameEntity> createWrapper(GameEntity entity) {
        LambdaQueryWrapper<GameEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        return wrappers;
    }
}
