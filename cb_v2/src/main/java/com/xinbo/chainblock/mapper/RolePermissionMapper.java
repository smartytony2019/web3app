package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.admin.RolePermissionEntity;
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
public interface RolePermissionMapper extends BaseMapper<RolePermissionEntity> {

    List<RolePermissionEntity> findByRoles(@Param("list") List<Integer> list);
    int deleteByPermission(@Param("permissionId") int permissionId);
    int deleteByRole(@Param("roleId") int roleId);

    @Select("select * from t_role_permission where role_id=#{roleId}")
    List<RolePermissionEntity> findByRoleId(@Param("roleId") int roleId);

}
