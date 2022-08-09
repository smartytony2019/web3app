package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.hash.HashPlayEntity;
import com.xinbo.chainblock.mapper.HashPlayMapper;
import com.xinbo.chainblock.service.HashPlayService;
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
public class HashPlayServiceImpl extends ServiceImpl<HashPlayMapper, HashPlayEntity> implements HashPlayService {

    @Autowired
    private HashPlayMapper hashPlayMapper;


    @Override
    public HashPlayEntity findById(int id) {
        return hashPlayMapper.selectById(id);
    }

    @Override
    public List<HashPlayEntity> findByGameId(int gameId) {
        return hashPlayMapper.selectList(this.createWrapper(HashPlayEntity.builder().gameId(gameId).build()));
    }

    @Override
    public List<HashPlayEntity> findAll() {
        return hashPlayMapper.selectList(this.createWrapper(HashPlayEntity.builder().build()));
    }


    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<HashPlayEntity> createWrapper(HashPlayEntity entity) {
        LambdaQueryWrapper<HashPlayEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getGameId())) {
            wrappers.eq(HashPlayEntity::getGameId, entity.getGameId());
        }
        return wrappers;
    }
}
