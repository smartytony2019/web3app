package com.xinbo.chainblock.service;

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
}
