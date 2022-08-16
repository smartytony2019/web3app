package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.TransferEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc file desc
 */
@Mapper
public interface TransferMapper extends BaseMapper<TransferEntity> {

    @Select("select * from t_transfer where status = 0 and expired > #{expired} limit 50")
    List<TransferEntity> findUnconfirmed(@Param("expired") long expired);

    @Select("select * from t_transfer where transaction_id = #{transactionId} limit 1")
    TransferEntity findByTransactionId(@Param("transactionId") String transactionId);
}
