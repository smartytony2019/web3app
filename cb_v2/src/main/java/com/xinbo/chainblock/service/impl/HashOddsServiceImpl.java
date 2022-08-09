package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.hash.HashOddsEntity;
import com.xinbo.chainblock.mapper.HashOddsMapper;
import com.xinbo.chainblock.service.HashOddsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:21 下午
 * @desc file desc
 */
@Service
public class HashOddsServiceImpl extends ServiceImpl<HashOddsMapper, HashOddsEntity> implements HashOddsService {

    @Autowired
    private HashOddsMapper hashOddsMapper;



    @Override
    public HashOddsEntity findById(int id) {
        return hashOddsMapper.selectById(id);
    }

    @Override
    public List<HashOddsEntity> findByGameId(int gameId) {
        HashOddsEntity entity = HashOddsEntity.builder().gameId(gameId).build();
        return hashOddsMapper.selectList(this.createWrapper(entity));
    }

    @Override
    public List<HashOddsEntity> findByCode(List<String> codes) {
        return hashOddsMapper.findByCode(codes);
    }


    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<HashOddsEntity> createWrapper(HashOddsEntity entity) {
        LambdaQueryWrapper<HashOddsEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getCode())) {
            wrappers.eq(HashOddsEntity::getCode, entity.getCode());
        }
        if (!ObjectUtils.isEmpty(entity.getGameId()) && entity.getGameId()>0) {
            wrappers.eq(HashOddsEntity::getGameId, entity.getGameId());
        }
        return wrappers;
    }
}
