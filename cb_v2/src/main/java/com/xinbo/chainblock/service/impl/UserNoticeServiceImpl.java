package com.xinbo.chainblock.service.impl;

import com.xinbo.chainblock.entity.admin.UserNoticeEntity;
import com.xinbo.chainblock.mapper.UserNoticeMapper;
import com.xinbo.chainblock.service.UserNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserNoticeServiceImpl implements UserNoticeService {

    @Autowired
    private UserNoticeMapper userNoticeMapper;

    @Override
    public boolean insert(UserNoticeEntity entity) {
        return userNoticeMapper.insert(entity)>0;
    }
}
