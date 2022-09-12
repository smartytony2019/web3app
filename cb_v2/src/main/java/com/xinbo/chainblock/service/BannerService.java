package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.admin.BannerEntity;

import java.util.List;

public interface BannerService {

    boolean insert(BannerEntity entity);

    boolean update(BannerEntity entity);

    List<BannerEntity> findall();
}
