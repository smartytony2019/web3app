package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.GameDto;
import com.xinbo.chainblock.dto.HashBetDto;
import com.xinbo.chainblock.entity.GameEntity;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.mapper.GameMapper;
import com.xinbo.chainblock.service.GameService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class GameServiceImpl extends ServiceImpl<GameMapper, GameEntity> implements GameService {

    @Autowired
    private GameMapper gameMapper;


    @Override
    public GameEntity findById(int id) {
        return gameMapper.selectById(id);
    }

    @Override
    public List<GameEntity> findAll() {
        return gameMapper.selectList(this.createWrapper(GameEntity.builder().build()));
    }

    @Override
    public List<GameEntity> findOffline() {
        return gameMapper.findOffline();
    }

    @Override
    public boolean update(GameEntity entity) {
        return gameMapper.updateById(entity) > 0;
    }

    @Override
    public BasePageBo findPage(GameEntity entity, long current, long size) {
        Page<GameEntity> page = new Page<>(current, size);
        page.addOrder(OrderItem.desc("id"));
        IPage<GameEntity> iPage = gameMapper.selectPage(page, this.createWrapper(entity));
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), GameDto.class)).build();
    }


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<GameEntity> createWrapper(GameEntity entity) {
        LambdaQueryWrapper<GameEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        return wrappers;
    }
}
