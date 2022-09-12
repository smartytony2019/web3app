package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.activity.ActivityCateEntity;
import com.xinbo.chainblock.entity.admin.BannerEntity;
import com.xinbo.chainblock.entity.admin.RoleEntity;
import com.xinbo.chainblock.mapper.ActivityCateMapper;
import com.xinbo.chainblock.mapper.BannerMapper;
import com.xinbo.chainblock.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl extends ServiceImpl<ActivityCateMapper, ActivityCateEntity> implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public boolean insert(BannerEntity entity) {
        return bannerMapper.insert(entity)>0;
    }

    @Override
    public boolean update(BannerEntity entity) {
        return bannerMapper.updateById(entity)>0;
    }

    @Override
    public List<BannerEntity> findall() {
        return bannerMapper.findAll();
    }
}
