package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.MemberFlowDto;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.MemberRecordEntity;
import com.xinbo.chainblock.mapper.MemberFlowMapper;
import com.xinbo.chainblock.service.MemberFlowService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class MemberFlowServiceImpl extends ServiceImpl<MemberFlowMapper, MemberFlowEntity> implements MemberFlowService {

    @Autowired
    private MemberFlowMapper memberFlowMapper;


    @Override
    public BasePageBo findPage(MemberFlowEntity entity, long current, long size) {
        Page<MemberFlowEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("create_time"));
        IPage<MemberFlowEntity> iPage = memberFlowMapper.selectPage(page, this.createWrapper(entity));
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), MemberFlowDto.class)).build();
    }

    @Override
    public BasePageBo findPage(MemberFlowEntity entity, long current, long size, Date start, Date end) {
        Page<MemberFlowEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("create_time"));

        LambdaQueryWrapper<MemberFlowEntity> wrapper = this.createWrapper(entity);
        if (!ObjectUtils.isEmpty(start) && !ObjectUtils.isEmpty(end)) {
            wrapper.ge(MemberFlowEntity::getCreateTime, start).le(MemberFlowEntity::getCreateTime, end);
        }
        IPage<MemberFlowEntity> iPage = memberFlowMapper.selectPage(page, wrapper);
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), MemberFlowDto.class)).build();
    }

    @Override
    public boolean insert(MemberFlowEntity entity) {
        return memberFlowMapper.insert(entity)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return memberFlowMapper.deleteById(id)>0;
    }

    @Override
    public boolean update(MemberFlowEntity entity) {
        return memberFlowMapper.updateById(entity)>0;
    }


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<MemberFlowEntity> createWrapper(MemberFlowEntity entity) {
        LambdaQueryWrapper<MemberFlowEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(MemberFlowEntity::getUsername, entity.getUsername());
        }
        if (!StringUtils.isEmpty(entity.getItemCode()) && entity.getItemCode() > 0) {
            wrappers.eq(MemberFlowEntity::getItemCode, entity.getItemCode());
        }
        return wrappers;
    }
}
