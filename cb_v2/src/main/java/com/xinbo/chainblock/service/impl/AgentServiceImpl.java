package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.AgentDto;
import com.xinbo.chainblock.dto.HashBetDto;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.FinanceEntity;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.mapper.AgentMapper;
import com.xinbo.chainblock.service.AgentService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class AgentServiceImpl extends ServiceImpl<AgentMapper, AgentEntity> implements AgentService {

    @Autowired
    private AgentMapper agentMapper;


    @Override
    public boolean insert(AgentEntity entity) {
        return agentMapper.insert(entity) > 0;
    }

    @Override
    public List<AgentEntity> findAll(int skip, int size) {
        return agentMapper.findAll(skip, size);
    }

    @Override
    public boolean setChild(int id, String childStr) {
        return agentMapper.setChild(id, childStr) > 0;
    }

    @Override
    public AgentEntity findByUid(int id) {
        return agentMapper.findByUid(id);
    }

    @Override
    public AgentEntity findByUsername(String username) {
        return agentMapper.findByUsername(username);
    }

    @Override
    public List<AgentEntity> direct(int uid) {
        return agentMapper.directly(uid);
    }

    @Override
    public BasePageBo findPage(AgentEntity entity, long current, long size) {
        Page<AgentEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.desc("id"));
        LambdaQueryWrapper<AgentEntity> wrapper = this.createWrapper(entity);

        if (StringUtils.isEmpty(entity.getChild()) && (StringUtils.isEmpty(entity.getPUid()) || entity.getPUid() <= 0)) {
            wrapper.ne(AgentEntity::getChild, "");
        }

        if (!StringUtils.isEmpty(entity.getPUid()) && entity.getPUid() > 0) {
            wrapper.eq(AgentEntity::getUid, entity.getPUid());
        }

        if (!StringUtils.isEmpty(entity.getChild())) {
            wrapper.in(AgentEntity::getUid, Arrays.asList(entity.getChild().split(",")));
        }

        IPage<AgentEntity> iPage = agentMapper.selectPage(page, wrapper);
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), AgentDto.class)).build();
    }


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<AgentEntity> createWrapper(AgentEntity entity) {
        LambdaQueryWrapper<AgentEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        return wrappers;
    }
}
