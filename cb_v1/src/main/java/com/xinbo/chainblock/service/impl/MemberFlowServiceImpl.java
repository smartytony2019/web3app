package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.dto.UserFlowDto;
import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.mapper.MemberFlowMapper;
import com.xinbo.chainblock.service.MemberFlowService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
    public BasePage findPage(MemberFlowEntity entity, long current, long size) {
        Page<MemberFlowEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("create_time"));
        IPage<MemberFlowEntity> iPage = memberFlowMapper.selectPage(page, this.createWrapper(entity));
        return BasePage.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), UserFlowDto.class)).build();
    }



    /**
     * 创建查询条件
     *
     * @param entity  实体
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
        if (!StringUtils.isEmpty(entity.getItemCode())) {
            wrappers.eq(MemberFlowEntity::getItemCode, entity.getItemCode());
        }
        return wrappers;
    }
}
