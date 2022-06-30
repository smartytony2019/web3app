package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.modal.Do.ExpectDo;
import com.xinbo.chainblock.mapper.ExpectMapper;
import com.xinbo.chainblock.service.ExpectService;
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
public class ExpectServiceImpl extends ServiceImpl<ExpectMapper, ExpectDo> implements ExpectService {

    @Autowired
    private ExpectMapper expectMapper;

    @Override
    public boolean insert(ExpectDo entity) {
        return expectMapper.insert(entity) > 0;
    }

    /**
     * 创建查询条件
     *
     * @param entity  实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<ExpectDo> createWrapper(ExpectDo entity) {
        LambdaQueryWrapper<ExpectDo> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getGameId())) {
            wrappers.eq(ExpectDo::getGameId, entity.getGameId());
        }
        if (!StringUtils.isEmpty(entity.getNum())) {
            wrappers.eq(ExpectDo::getNum, entity.getNum());
        }
        return wrappers;
    }
}
