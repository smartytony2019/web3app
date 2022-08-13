package com.xinbo.chainblock.service;
import com.xinbo.chainblock.entity.FinanceEntity;
import com.xinbo.chainblock.entity.WalletEntity;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface FinanceService {

    boolean insert(FinanceEntity entity);

    FinanceEntity findByUid(int uid);
}
