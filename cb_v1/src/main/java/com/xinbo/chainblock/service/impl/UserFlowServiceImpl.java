package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.dto.LotteryBetDto;
import com.xinbo.chainblock.dto.UserFlowDto;
import com.xinbo.chainblock.entity.LotteryBetEntity;
import com.xinbo.chainblock.entity.UserFlowEntity;
import com.xinbo.chainblock.entity.WalletEntity;
import com.xinbo.chainblock.mapper.UserFlowMapper;
import com.xinbo.chainblock.mapper.UserMapper;
import com.xinbo.chainblock.mapper.WalletMapper;
import com.xinbo.chainblock.service.UserFlowService;
import com.xinbo.chainblock.service.WalletService;
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
public class UserFlowServiceImpl extends ServiceImpl<UserFlowMapper, UserFlowEntity> implements UserFlowService {

    @Autowired
    private UserFlowMapper userFlowMapper;



    @Override
    public BasePage findPage(UserFlowEntity entity, long current, long size) {
        Page<UserFlowEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("create_time"));
        IPage<UserFlowEntity> iPage = userFlowMapper.selectPage(page, this.createWrapper(entity));
        return BasePage.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), UserFlowDto.class)).build();
    }



    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<UserFlowEntity> createWrapper(UserFlowEntity entity) {
        LambdaQueryWrapper<UserFlowEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(UserFlowEntity::getUsername, entity.getUsername());
        }
        if (!StringUtils.isEmpty(entity.getItemCode())) {
            wrappers.eq(UserFlowEntity::getItemCode, entity.getItemCode());
        }
        return wrappers;
    }
}
