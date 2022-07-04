package com.xinbo.chainblock.service;

import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.dto.LotteryBetDto;
import com.xinbo.chainblock.entity.LotteryBetEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface LotteryBetService {

    LotteryBetDto findById(int id);

    boolean insert(LotteryBetEntity entity);

    List<LotteryBetEntity> find(LotteryBetEntity entity);

    BasePage findPage(LotteryBetEntity dto, long current, long size);

    List<LotteryBetEntity> unsettle(String num, int size);

    boolean settle(List<LotteryBetEntity> list);
}
