package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.mapper.PermissionMapper;
import com.xinbo.chainblock.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public boolean insert(PermissionEntity entity) {
        return permissionMapper.insert(entity)>0;
    }

    @Override
    public boolean update(PermissionEntity entity) {
        return permissionMapper.updateById(entity)>0;
    }

    @Override
    public List<PermissionEntity> findall() {
        return permissionMapper.findAll();
    }

}
