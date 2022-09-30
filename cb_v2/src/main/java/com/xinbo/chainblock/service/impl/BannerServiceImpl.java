package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.admin.BannerEntity;
import com.xinbo.chainblock.entity.admin.NoticeEntity;
import com.xinbo.chainblock.entity.admin.RoleEntity;
import com.xinbo.chainblock.mapper.BannerMapper;
import com.xinbo.chainblock.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, BannerEntity> implements BannerService {

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
    public List<BannerEntity> findAll(BannerEntity entity) {

        return bannerMapper.selectList(this.createWrapper(entity));
    }

    @Override
    public boolean delete(int id) {
        return bannerMapper.deleteById(id)>0;
    }

    @Override
    public BannerEntity find(int id) {
        return bannerMapper.selectById(id);
    }

    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<BannerEntity> createWrapper(BannerEntity entity) {
        LambdaQueryWrapper<BannerEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }


        if (!StringUtils.isEmpty(entity.getLangCode())) {
            wrappers.eq(BannerEntity::getLangCode, entity.getLangCode());
        }

        return wrappers;
    }
}
