package com.xinbo.chainblock.service;

import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.dto.LotteryBetDto;
import com.xinbo.chainblock.entity.HashBetEntity;

import java.util.Date;
import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface HashBetService {

    LotteryBetDto findById(int id);

    boolean insert(HashBetEntity entity);

    List<HashBetEntity> find(HashBetEntity entity);

    BasePage findPage(HashBetEntity entity, long current, long size);

    BasePage findPage(HashBetEntity entity, long current, long size, Date start, Date end);

    List<HashBetEntity> unsettle(String num, int size);

    boolean settle(List<HashBetEntity> list);
}
