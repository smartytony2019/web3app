package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.HashResultEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
}
