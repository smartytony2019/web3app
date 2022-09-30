package com.xinbo.chainblock.service;

import com.xinbo.chainblock.entity.MemberFlowEntity;
import com.xinbo.chainblock.entity.admin.BannerEntity;
import com.xinbo.chainblock.entity.admin.PermissionEntity;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author pzblog
 * @since 2020-06-28
 */
public interface PermissionService {

    boolean insert(PermissionEntity entity);
    boolean update(PermissionEntity entity);
    List<PermissionEntity> findAll();
    PermissionEntity find(int id);
    boolean delete(int id);
    boolean findByParentId(PermissionEntity entity);
    List<PermissionEntity> allMenu();
    List<PermissionEntity> AllMenuExcludeButton();
    List<PermissionEntity> roleMenu(int roleId);
    List<PermissionEntity> getRolePermission(int roleId);
}
