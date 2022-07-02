package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.UserEntity;
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
public interface UserMapper extends BaseMapper<UserEntity> {

    @Update("update t_user set money = money + #{entity.money}, version = version + 1 where id = #{entity.id} and version = #{entity.version}")
    int increment(@Param("entity") UserEntity entity);


    @Select("select version from t_user where id = #{id}")
    int findVersionById(@Param("id") int id);
}
