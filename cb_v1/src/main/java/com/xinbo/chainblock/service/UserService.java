package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.admin.PermissionEntity;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author pzblog
 * @since 2020-06-28
 */
public interface UserService {

    List<Integer> findPermission(int userId);

    List<PermissionEntity> menu(int userId);

}
