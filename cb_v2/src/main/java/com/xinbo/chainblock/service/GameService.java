package com.xinbo.chainblock.service;

import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.GameEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface GameService {

    GameEntity findById(int id);

    List<GameEntity> findAll();

    List<GameEntity> findOffline();

    boolean update(GameEntity entity);

    BasePageBo findPage(GameEntity entity, long current, long size);
}
