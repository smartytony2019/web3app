package com.xinbo.chainblock.service;


import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.AgentCommissionEntity;
import com.xinbo.chainblock.entity.AgentEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface AgentService {

    boolean insert(AgentEntity entity);

    List<AgentEntity> findAll(int skip, int size);

    boolean setChild(int id, String childStr);

    AgentEntity findByUid(int id);

    AgentEntity findByUsername(String username);

    /**
     * 直属
     * @param uid
     * @return
     */
    List<AgentEntity> direct(int uid);

    BasePageBo findPage(AgentEntity entity, long current, long size);
}
