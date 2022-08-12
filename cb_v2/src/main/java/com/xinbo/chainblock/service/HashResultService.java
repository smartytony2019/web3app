package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.hash.HashResultEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface HashResultService {

    List<HashResultEntity> findRecord(HashResultEntity entity);

    boolean insert(HashResultEntity entity);

    HashResultEntity unsettle();

    boolean settled(int id);
}
