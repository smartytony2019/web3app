package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.LotteryPlayCodeEntity;
import com.xinbo.chainblock.entity.LotteryPlayEntity;
import com.xinbo.chainblock.mapper.LotteryPlayCodeMapper;
import com.xinbo.chainblock.mapper.LotteryPlayMapper;
import com.xinbo.chainblock.service.LotteryPlayCodeService;
import com.xinbo.chainblock.service.LotteryPlayService;
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
public class LotteryPlayCodeServiceImpl extends ServiceImpl<LotteryPlayCodeMapper, LotteryPlayCodeEntity> implements LotteryPlayCodeService {

    @Autowired
    private LotteryPlayCodeMapper lotteryPlayCodeMapper;



    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<LotteryPlayCodeEntity> createWrapper(LotteryPlayCodeEntity entity) {
        LambdaQueryWrapper<LotteryPlayCodeEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getPlayCode())) {
            wrappers.eq(LotteryPlayCodeEntity::getPlayCode, entity.getPlayCode());
        }
        return wrappers;
    }




}
