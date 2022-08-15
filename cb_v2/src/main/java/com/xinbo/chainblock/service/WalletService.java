package com.xinbo.chainblock.service;
import com.xinbo.chainblock.entity.WalletEntity;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface WalletService {

    WalletEntity findByAddress(String address);

    boolean insert(WalletEntity entity);

    WalletEntity findByUid(int uid);

    WalletEntity findMain();
}
