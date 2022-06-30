package com.xinbo.chainblock.service;

import com.xinbo.chainblock.modal.Do.WalletDo;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface WalletService {

    WalletDo findByAddress(String address);

}
