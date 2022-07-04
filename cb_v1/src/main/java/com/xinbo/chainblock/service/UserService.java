package com.xinbo.chainblock.service;
import com.xinbo.chainblock.entity.UserEntity;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
public interface UserService {

    boolean insert();


    UserEntity findById(int id);

    boolean increment(UserEntity entity);

    boolean register(UserEntity entity, int code);
}
