package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.hash.HashOddsEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface HashOddsService {


    HashOddsEntity findById(int id);

    List<HashOddsEntity> findByGameId(int id);

    List<HashOddsEntity> findByCode(List<String> codes);

    HashOddsEntity find(int gameId, String nameZh);
}
