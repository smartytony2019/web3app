package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.HashResultEntity;
import com.xinbo.chainblock.entity.WalletEntity;
import com.xinbo.chainblock.mapper.HashResultMapper;
import com.xinbo.chainblock.mapper.WalletMapper;
import com.xinbo.chainblock.service.HashResultService;
import com.xinbo.chainblock.service.WalletService;
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
public class HashResultServiceImpl extends ServiceImpl<HashResultMapper, HashResultEntity> implements HashResultService {

    @Autowired
    private HashResultMapper hashResultMapper;



    @Override
    public boolean insert(HashResultEntity entity) {
        return hashResultMapper.insert(entity) > 0;
    }



    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<WalletEntity> createWrapper(WalletEntity entity) {
        LambdaQueryWrapper<WalletEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(WalletEntity::getUsername, entity.getUsername());
        }
        if (!StringUtils.isEmpty(entity.getAddress())) {
            wrappers.eq(WalletEntity::getAddress, entity.getAddress());
        }
        return wrappers;
    }
}
