package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.HashResultDto;
import com.xinbo.chainblock.dto.MemberFlowDto;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.hash.HashResultEntity;
import com.xinbo.chainblock.mapper.HashResultMapper;
import com.xinbo.chainblock.service.HashResultService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class HashResultServiceImpl extends ServiceImpl<HashResultMapper, HashResultEntity> implements HashResultService {

    @Autowired
    private HashResultMapper hashResultMapper;


    @Override
    public boolean insert(HashResultEntity entity) {
        return hashResultMapper.insert(entity) > 0;
    }


    public List<HashResultEntity> findRecord(HashResultEntity entity) {
        return hashResultMapper.findRecord(entity);
    }


    @Override
    public HashResultEntity unsettle() {
        return hashResultMapper.unsettle();
    }

    @Override
    public boolean settled(int id) {
        return hashResultMapper.settled(id) > 0;
    }

    @Override
    public BasePageBo findPage(HashResultEntity entity, long current, long size) {
        Page<HashResultEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.desc("open_timestamp"));
        IPage<HashResultEntity> iPage = hashResultMapper.selectPage(page, this.createWrapper(entity));
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), HashResultDto.class)).build();
    }


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<HashResultEntity> createWrapper(HashResultEntity entity) {
        LambdaQueryWrapper<HashResultEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getGameId()) && entity.getGameId() > 0) {
            wrappers.eq(HashResultEntity::getGameId, entity.getGameId());
        }
        if (!StringUtils.isEmpty(entity.getSn())) {
            wrappers.eq(HashResultEntity::getSn, entity.getSn());
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(HashResultEntity::getUsername, entity.getUsername());
        }
        return wrappers;
    }
}
