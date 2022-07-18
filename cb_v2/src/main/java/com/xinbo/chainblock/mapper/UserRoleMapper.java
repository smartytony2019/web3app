package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.admin.UserRoleEntity;
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
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {

    @Select("select * from t_user_role where user_id = #{userId}")
    List<UserRoleEntity> findByUserId(@Param("userId") int userId);
}
