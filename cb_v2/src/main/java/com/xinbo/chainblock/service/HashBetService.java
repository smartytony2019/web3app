package com.xinbo.chainblock.service;

import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.hash.HashResultEntity;

import java.util.Date;
import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface HashBetService {

    HashBetEntity findById(int id);

    boolean insert(HashBetEntity entity);

    boolean bet(HashBetEntity bet, MemberEntity member, MemberFlowEntity memberFlowEntity, HashResultEntity result);

    HashBetEntity find(HashBetEntity entity);

    List<HashBetEntity> findList(HashBetEntity entity);

    BasePageBo findPage(HashBetEntity entity, long current, long size);

    BasePageBo findPage(HashBetEntity entity, long current, long size, Date start, Date end);

    HashBetEntity unsettle();

    boolean settle(HashBetEntity bet, HashResultEntity result);

    HashBetEntity findOrder(String sn);
}
