package com.xinbo.chainblock.service;

import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.PermissionDto;
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

    BasePageBo findPage(UserEntity entity, long current, long size);

     List<PermissionDto> allMenu(int userId);

    List<PermissionEntity> AllMenuExcludeButton(int userId);

    boolean update(UserEntity entity);

    boolean delete(int id);

    boolean isUserNameExist(String userName);
    boolean insert(UserEntity entity);
}
