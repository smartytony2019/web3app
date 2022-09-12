package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.UserDto;
import com.xinbo.chainblock.entity.admin.RoleEntity;
import com.xinbo.chainblock.entity.admin.UserEntity;
import com.xinbo.chainblock.mapper.RoleMapper;
import com.xinbo.chainblock.service.RoleService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean insert(RoleEntity entity) {

        return roleMapper.insert(entity)>0;
    }

    @Override
    public boolean update(RoleEntity entity) {
        return roleMapper.updateById(entity)>0;
    }

    @Override
    public List<RoleEntity> findAll() {
        return roleMapper.findAll();
    }


}
