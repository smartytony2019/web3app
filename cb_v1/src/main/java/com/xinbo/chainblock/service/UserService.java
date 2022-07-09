package com.xinbo.chainblock.service;

import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.entity.admin.UserEntity;

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

    UserEntity findById(int userId);

    BasePage findPage(UserEntity entity, long current, long size);
}
