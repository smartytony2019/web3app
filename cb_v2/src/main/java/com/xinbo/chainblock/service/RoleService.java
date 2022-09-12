package com.xinbo.chainblock.service;

import com.xinbo.chainblock.bo.BasePageBo;
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

    List<RoleEntity> findAll();
}
