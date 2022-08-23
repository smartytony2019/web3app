package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.MemberFlowDto;
import com.xinbo.chainblock.entity.MemberRecordEntity;
import com.xinbo.chainblock.mapper.MemberRecordMapper;
import com.xinbo.chainblock.service.MemberRecordService;
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
public class MemberRecordServiceImpl extends ServiceImpl<MemberRecordMapper, MemberRecordEntity> implements MemberRecordService {

    @Autowired
    private MemberRecordMapper memberRecordMapper;



    @Override
    public BasePageBo findPage(MemberRecordEntity entity, long current, long size) {
        Page<MemberRecordEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("login_time"));
        IPage<MemberRecordEntity> iPage = memberRecordMapper.selectPage(page, this.createWrapper(entity));
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), MemberFlowDto.class)).build();
    }

    @Override
    public BasePageBo findPage(MemberRecordEntity entity, long current, long size, Date start, Date end) {
        Page<MemberRecordEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("login_time"));

        LambdaQueryWrapper<MemberRecordEntity> wrapper = this.createWrapper(entity);
        if(!ObjectUtils.isEmpty(start) && !ObjectUtils.isEmpty(end)) {
            wrapper.ge(MemberRecordEntity::getLoginTime, start).le(MemberRecordEntity::getLoginTime, end);
        }
        IPage<MemberRecordEntity> iPage = memberRecordMapper.selectPage(page, wrapper);
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), MemberFlowDto.class)).build();
    }


    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<MemberRecordEntity> createWrapper(MemberRecordEntity entity) {
        LambdaQueryWrapper<MemberRecordEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(MemberRecordEntity::getUsername, entity.getUsername());
        }
        if (!StringUtils.isEmpty(entity.getDevice())) {
            wrappers.eq(MemberRecordEntity::getDevice, entity.getDevice());
        }
        return wrappers;
    }
}
