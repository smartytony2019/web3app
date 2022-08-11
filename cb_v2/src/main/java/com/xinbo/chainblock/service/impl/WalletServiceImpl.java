package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.WalletEntity;
import com.xinbo.chainblock.mapper.WalletMapper;
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
public class WalletServiceImpl extends ServiceImpl<WalletMapper, WalletEntity> implements WalletService {

    @Autowired
    private WalletMapper walletMapper;


    public WalletEntity findByAddress(String address) {
        WalletEntity entity = WalletEntity.builder().addressHex(address).build();
        LambdaQueryWrapper<WalletEntity> wrappers = this.createWrapper(entity);
        return walletMapper.selectOne(wrappers);
    }

    @Override
    public boolean insert(WalletEntity entity) {
        return walletMapper.insert(entity) > 0;
    }

    @Override
    public WalletEntity findByUid(int uid) {
        LambdaQueryWrapper<WalletEntity> wrapper = this.createWrapper(WalletEntity.builder().uid(uid).build());
        return walletMapper.selectOne(wrapper);
    }


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<WalletEntity> createWrapper(WalletEntity entity) {
        LambdaQueryWrapper<WalletEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUid())) {
            wrappers.eq(WalletEntity::getUid, entity.getUid());
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(WalletEntity::getUsername, entity.getUsername());
        }
        if (!StringUtils.isEmpty(entity.getAddressBase58())) {
            wrappers.eq(WalletEntity::getAddressBase58, entity.getAddressBase58());
        }
        return wrappers;
    }


}
