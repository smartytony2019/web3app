package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.RechargeEntity;
import com.xinbo.chainblock.entity.WalletEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc file desc
 */
@Mapper
public interface WalletMapper extends BaseMapper<WalletEntity> {
}
