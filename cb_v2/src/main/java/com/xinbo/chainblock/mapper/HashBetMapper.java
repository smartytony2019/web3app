package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
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
public interface HashBetMapper extends BaseMapper<HashBetEntity> {

    @Select("select * from t_hash_bet where status = 0 limit 1")
    HashBetEntity unsettle();

    @Update("update t_hash_bet set hash_result=#{entity.hashResult},profit_money=#{entity.profitMoney},payout_money=#{entity.payoutMoney},update_time=#{entity.updateTime},flag=#{entity.flag},status=#{entity.status} where id = #{entity.id}")
    int settle(@Param("entity") HashBetEntity entity);
}
