package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.dto.LotteryBetDto;
import com.xinbo.chainblock.entity.LotteryBetEntity;
import com.xinbo.chainblock.mapper.LotteryBetMapper;
import com.xinbo.chainblock.service.LotteryBetService;
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
public class LotteryBetServiceImpl extends ServiceImpl<LotteryBetMapper, LotteryBetEntity> implements LotteryBetService {

    @Autowired
    private LotteryBetMapper lotteryBetMapper;


    @Override
    public LotteryBetDto findById(int id) {
        LotteryBetEntity entity = lotteryBetMapper.selectById(id);
        return MapperUtil.to(entity, LotteryBetDto.class);
    }

    @Override
    public boolean insert(LotteryBetDto dto) {
        LotteryBetEntity entity = MapperUtil.to(dto, LotteryBetEntity.class);
        return lotteryBetMapper.insert(entity)> 0;
    }

    @Override
    public List<LotteryBetDto> find(LotteryBetDto dto) {
        LotteryBetEntity entity = MapperUtil.to(dto, LotteryBetEntity.class);
        List<LotteryBetEntity> betEntityList = lotteryBetMapper.selectList(this.createWrapper(entity));
        return MapperUtil.many(betEntityList, LotteryBetDto.class);
    }


    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<LotteryBetEntity> createWrapper(LotteryBetEntity entity) {
        LambdaQueryWrapper<LotteryBetEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUid())) {
            wrappers.eq(LotteryBetEntity::getUid, entity.getUid());
        }
        if (!StringUtils.isEmpty(entity.getNum())) {
            wrappers.eq(LotteryBetEntity::getNum, entity.getNum());
        }
        if (!StringUtils.isEmpty(entity.getHashResult())) {
            wrappers.eq(LotteryBetEntity::getHashResult, entity.getHashResult());
        }
        if (!StringUtils.isEmpty(entity.getGameId())) {
            wrappers.eq(LotteryBetEntity::getGameId, entity.getGameId());
        }
        return wrappers;
    }
}
