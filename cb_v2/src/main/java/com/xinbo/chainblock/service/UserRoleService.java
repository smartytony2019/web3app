package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.admin.UserRoleEntity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author pzblog
 * @since 2020-06-28
 */
public interface UserRoleService {

    boolean deleteByRole(int roleId);

    boolean deleteByUser(int userId);

    boolean insert(UserRoleEntity entity);
}
