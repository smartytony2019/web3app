package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.UserEntity;
import com.xinbo.chainblock.mapper.UserMapper;
import com.xinbo.chainblock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
//@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper, UserEntity> implements UserService {



    @Autowired
//    private UserMapper userMapper;


    public boolean create() {
//        UserEntity entity = UserEntity.builder()
//                .name("jack").createTime(new Date()).money(1000F).salt("123").version(1)
//                .build();
//        userMapper.insert(entity);
        return true;
    }





}
