package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.hash.HashPlayEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface HashPlayService {

    HashPlayEntity findById(int id);

    List<HashPlayEntity> findByGameId(int gameId);

    List<HashPlayEntity> findAll();

}
