package com.xinbo.chainblock.service;

import com.xinbo.chainblock.dto.LotteryBetDto;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface LotteryBetService {

    LotteryBetDto findById(int id);

    boolean insert(LotteryBetDto dto);

    List<LotteryBetDto> find(LotteryBetDto dto);

}
