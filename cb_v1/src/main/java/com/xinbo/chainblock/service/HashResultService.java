package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.HashResultEntity;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface HashResultService {

    boolean insert(HashResultEntity entity);

    HashResultEntity unsettle();

    boolean settled(int id);
}
