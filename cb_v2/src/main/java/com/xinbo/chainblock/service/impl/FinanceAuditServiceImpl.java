package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.FinanceAuditDto;
import com.xinbo.chainblock.entity.FinanceAuditEntity;
import com.xinbo.chainblock.mapper.FinanceAuditMapper;
import com.xinbo.chainblock.service.FinanceAuditService;
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
public class FinanceAuditServiceImpl extends ServiceImpl<FinanceAuditMapper, FinanceAuditEntity> implements FinanceAuditService {

    @Autowired
    private FinanceAuditMapper financeAuditMapper;

    /**
     * 插入
     * @param entity
     * @return
     */
    @Override
    public boolean insert(FinanceAuditEntity entity) {
        return financeAuditMapper.insert(entity) > 0;
    }

    /**
     * 根据uid查找
     * @param uid
     * @return
     */
    @Override
    public FinanceAuditEntity findByUid(int uid) {
        LambdaQueryWrapper<FinanceAuditEntity> wrapper = this.createWrapper(FinanceAuditEntity.builder().uid(uid).build());
        return financeAuditMapper.selectOne(wrapper);
    }


    @Override
    public BasePageBo findPage(FinanceAuditEntity entity, long current, long size) {
        Page<FinanceAuditEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.asc("create_time"));
        IPage<FinanceAuditEntity> iPage = financeAuditMapper.selectPage(page, this.createWrapper(entity));
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), FinanceAuditDto.class)).build();
    }


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<FinanceAuditEntity> createWrapper(FinanceAuditEntity entity) {
        LambdaQueryWrapper<FinanceAuditEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUid())) {
            wrappers.eq(FinanceAuditEntity::getUid, entity.getUid());
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(FinanceAuditEntity::getUsername, entity.getUsername());
        }
        return wrappers;
    }


}
