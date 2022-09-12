package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.entity.admin.RolePermissionEntity;

/**
 * <p>
 * 角色菜单关系表 服务类
 * </p>
 *
 * @author pzblog
 * @since 2020-06-28
 */
public interface RolePermissionService {

    boolean deleteByPermission(int permission);
    boolean deleteByRole(int role);
    boolean insert(RolePermissionEntity entity);
    boolean update(RolePermissionEntity entity);
}
