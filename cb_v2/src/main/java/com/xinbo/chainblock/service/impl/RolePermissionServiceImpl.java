package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.admin.RolePermissionEntity;
import com.xinbo.chainblock.mapper.RolePermissionMapper;
import com.xinbo.chainblock.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionEntity> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public boolean deleteByPermission(int permission) {
        return rolePermissionMapper.deleteByPermission(permission)>=0;
    }

    @Override
    public boolean deleteByRole(int role) {
        return rolePermissionMapper.deleteByRole(role)>=0;
    }

    @Override
    public boolean insert(RolePermissionEntity entity) {
        return rolePermissionMapper.insert(entity)>0;
    }

    @Override
    public boolean update(RolePermissionEntity entity) {
        return rolePermissionMapper.updateById(entity)>0;
    }

}
