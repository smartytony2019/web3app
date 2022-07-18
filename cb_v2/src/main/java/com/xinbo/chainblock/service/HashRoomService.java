package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.HashRoomEntity;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface HashRoomService {

    HashRoomEntity findById(int id);

    List<HashRoomEntity> findAll();

}
