package com.xinbo.chainblock.service;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.UserFlowEntity;
import com.xinbo.chainblock.entity.WalletEntity;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface UserFlowService {


    BasePage findPage(UserFlowEntity entity, long current, long size);
}
