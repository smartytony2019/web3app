package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.AgentEntity;
import com.xinbo.chainblock.entity.activity.ActivityEntity;
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
public interface ActivityMapper extends BaseMapper<ActivityEntity> {
    @Select("select * from t_activity where uid = #{code} limit 1")
    ActivityEntity findByUid(@Param("code") int code);

    @Select("select * from t_activity order by id asc limit #{skip}, #{size}")
    List<ActivityEntity> findAll(@Param("skip") int skip, @Param("size") int size);

    @Select("select * from t_activity where type = #{type} and is_enable = 1 limit 1 ")
    ActivityEntity findByType(@Param("type") int type);
}
