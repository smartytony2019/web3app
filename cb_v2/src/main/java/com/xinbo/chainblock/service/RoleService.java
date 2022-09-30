package com.xinbo.chainblock.service;

import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.admin.NoticeEntity;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.entity.admin.RoleEntity;

import java.util.List;


/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author pzblog
 * @since 2020-06-28
 */
public interface RoleService {

    boolean insert(RoleEntity entity);

    boolean update(RoleEntity entity);

    BasePageBo findPage(RoleEntity entity, long current, long size);

    RoleEntity find(int id);

    boolean delete(int id);

    List<RoleEntity> findAll();
    List<RoleEntity> getRoles(int userId);
}
