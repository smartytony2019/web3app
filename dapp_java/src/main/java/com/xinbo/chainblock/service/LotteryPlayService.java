package com.xinbo.chainblock.service;

import com.xinbo.chainblock.modal.Do.LotteryPlayDo;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface LotteryPlayService {

    LotteryPlayDo findById(int id);

    List<LotteryPlayDo> findAll();

}
