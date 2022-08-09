package com.xinbo.chainblock.service;

import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.dto.HashBetDto;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.hash.HashBetEntity;

import java.util.Date;
import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface HashBetService {

    HashBetDto findById(int id);

    boolean insert(HashBetEntity entity);

    boolean bet(HashBetEntity bet, MemberEntity member, MemberFlowEntity memberFlowEntity);

    List<HashBetEntity> find(HashBetEntity entity);

    BasePage findPage(HashBetEntity entity, long current, long size);

    BasePage findPage(HashBetEntity entity, long current, long size, Date start, Date end);

    List<HashBetEntity> unsettle(String num, int size);

    boolean settle(List<HashBetEntity> list);
}
