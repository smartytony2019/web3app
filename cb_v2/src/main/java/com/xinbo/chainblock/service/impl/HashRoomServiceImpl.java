package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.HashRoomEntity;
import com.xinbo.chainblock.mapper.HashRoomMapper;
import com.xinbo.chainblock.service.HashRoomService;
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
public class HashRoomServiceImpl extends ServiceImpl<HashRoomMapper, HashRoomEntity> implements HashRoomService {

    @Autowired
    private HashRoomMapper hashRoomMapper;


    @Override
    public HashRoomEntity findById(int id) {
        return hashRoomMapper.selectById(id);
    }

    @Override
    public List<HashRoomEntity> findAll() {
        return hashRoomMapper.selectList(this.createWrapper(HashRoomEntity.builder().build()));
    }


    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<HashRoomEntity> createWrapper(HashRoomEntity entity) {
        LambdaQueryWrapper<HashRoomEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getGameId())) {
            wrappers.eq(HashRoomEntity::getGameId, entity.getGameId());
        }
        return wrappers;
    }
}
