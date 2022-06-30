package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.modal.Do.LotteryBetDo;
import com.xinbo.chainblock.mapper.LotteryBetMapper;
import com.xinbo.chainblock.modal.Dto.LotteryBetDto;
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
public class LotteryBetServiceImpl extends ServiceImpl<LotteryBetMapper, LotteryBetDo> implements LotteryBetService {

    @Autowired
    private LotteryBetMapper lotteryBetMapper;


    @Override
    public boolean insert(LotteryBetDto dto) {
        LotteryBetDo entity = MapperUtil.to(dto, LotteryBetDo.class);
        return lotteryBetMapper.insert(entity)> 0;
    }

    @Override
    public List<LotteryBetDto> find(LotteryBetDto dto) {
        LotteryBetDo entity = MapperUtil.to(dto, LotteryBetDo.class);
        List<LotteryBetDo> betEntityList = lotteryBetMapper.selectList(this.createWrapper(entity));
        return MapperUtil.many(betEntityList, LotteryBetDto.class);
    }


    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<LotteryBetDo> createWrapper(LotteryBetDo entity) {
        LambdaQueryWrapper<LotteryBetDo> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getUid())) {
            wrappers.eq(LotteryBetDo::getUid, entity.getUid());
        }
        if (!StringUtils.isEmpty(entity.getNum())) {
            wrappers.eq(LotteryBetDo::getNum, entity.getNum());
        }
        if (!StringUtils.isEmpty(entity.getHashResult())) {
            wrappers.eq(LotteryBetDo::getHashResult, entity.getHashResult());
        }
        if (!StringUtils.isEmpty(entity.getGameId())) {
            wrappers.eq(LotteryBetDo::getGameId, entity.getGameId());
        }
        return wrappers;
    }
}
