package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.hash.HashBetEntity;
import com.xinbo.chainblock.entity.hash.HashOfflineBetEntity;
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
public interface HashOfflineBetMapper extends BaseMapper<HashOfflineBetEntity> {

    @Select("select * from t_hash_offline_bet where status = 0 limit 5")
    List<HashOfflineBetEntity> unsettle();

    @Update("update t_hash_offline_bet set block_hash=#{entity.blockHash},block_height=#{entity.blockHeight},network=#{entity.network},profit_money=#{entity.profitMoney},payout_money=#{entity.payoutMoney},update_time=#{entity.updateTime},update_timestamp=#{entity.updateTimestamp},flag=#{entity.flag},status=#{entity.status} where id = #{entity.id}")
    int settle(@Param("entity") HashOfflineBetEntity entity);

    int batchInsert(@Param("list") List<HashOfflineBetEntity> list);
}
