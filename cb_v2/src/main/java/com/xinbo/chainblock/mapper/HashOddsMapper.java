package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.hash.HashOddsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc file desc
 */
@Mapper
public interface HashOddsMapper extends BaseMapper<HashOddsEntity> {


    List<HashOddsEntity> findByCode(@Param("codes") List<String> codes);

    @Select("select * from t_hash_odds where game_id = #{gameId} and name_zh = #{nameZh} limit 1")
    HashOddsEntity find(@Param("gameId") int gameId, @Param("nameZh") String nameZh);
}
