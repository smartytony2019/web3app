package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.modal.Do.WalletDo;
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
public class WalletServiceImpl extends ServiceImpl<WalletMapper, WalletDo> implements WalletService {

    @Autowired
    private WalletMapper walletMapper;




    public WalletDo findByAddress(String address) {
        WalletDo entity = WalletDo.builder().address(address).build();
        LambdaQueryWrapper<WalletDo> wrappers = this.createWrapper(entity);
        return walletMapper.selectOne(wrappers);
    }


    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<WalletDo> createWrapper(WalletDo entity) {
        LambdaQueryWrapper<WalletDo> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(WalletDo::getUsername, entity.getUsername());
        }
        if (!StringUtils.isEmpty(entity.getAddress())) {
            wrappers.eq(WalletDo::getAddress, entity.getAddress());
        }
        return wrappers;
    }




}
