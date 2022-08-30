package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.hash.HashOfflineBetEntity;
import com.xinbo.chainblock.mapper.*;
import com.xinbo.chainblock.service.HashOfflineBetService;
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
public class HashOfflineBetServiceImpl extends ServiceImpl<HashOfflineBetMapper, HashOfflineBetEntity> implements HashOfflineBetService {

    @Autowired
    private HashOfflineBetMapper hashOfflineBetMapper;

    @Override
    public List<HashOfflineBetEntity> unsettle() {
        return hashOfflineBetMapper.unsettle();
    }


    @Override
    public boolean settle(HashOfflineBetEntity bet) {
        return hashOfflineBetMapper.settle(bet) > 0;
    }


    @Override
    public boolean batchInsert(List<HashOfflineBetEntity> list) {
        return hashOfflineBetMapper.batchInsert(list) > 0;
    }

    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<HashOfflineBetEntity> createWrapper(HashOfflineBetEntity entity) {
        LambdaQueryWrapper<HashOfflineBetEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getStatus()) && entity.getStatus() > 0) {
            wrappers.eq(HashOfflineBetEntity::getStatus, entity.getStatus());
        }
        return wrappers;
    }
}
