package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.hash.HashResultEntity;
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
public interface HashResultMapper extends BaseMapper<HashResultEntity> {


    @Select("select * from t_hash_result where is_settle = 0 order by num asc limit 1")
    HashResultEntity unsettle();

    @Update("update t_hash_result set is_settle = 1 where id = #{id}")
    int settled(@Param("id") int id);

    @Update("update t_hash_result set txID=#{entity.txID}, block_hash=#{entity.blockHash}, block_height=#{entity.blockHeight}, open_time=#{entity.openTime}, open_timestamp=#{entity.openTimestamp}, network=#{entity.network}, flag=#{entity.flag} where sn=#{entity.sn}")
    int update(@Param("entity") HashResultEntity entity);

    @Select("select block_hash, block_height, network, flag from t_hash_result where uid=#{entity.uid} and game_id=#{entity.gameId} and play_id=#{entity.playId} and block_height > 0 order by open_timestamp desc limit 30")
    List<HashResultEntity> findRecord(@Param("entity") HashResultEntity entity);

}
