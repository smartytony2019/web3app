package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.UserDto;
import com.xinbo.chainblock.entity.admin.*;
import com.xinbo.chainblock.mapper.RoleMapper;
import com.xinbo.chainblock.mapper.UserRoleMapper;
import com.xinbo.chainblock.service.RoleService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean insert(RoleEntity entity) {

        return roleMapper.insert(entity)>0;
    }

    @Override
    public boolean update(RoleEntity entity) {
        return roleMapper.updateById(entity)>0;
    }

    @Override
    public BasePageBo findPage(RoleEntity entity, long current, long size) {
        Page<RoleEntity> page = new Page<>(current, size);
        IPage<RoleEntity> iPage = roleMapper.selectPage(page,this.createWrapper(entity));
        List<RoleEntity> roleList = iPage.getRecords();
        return BasePageBo.builder().total(iPage.getTotal()).records(roleList).build();
    }

    @Override
    public RoleEntity find(int id) {
        return roleMapper.selectById(id);
    }

    @Override
    public boolean delete(int id) {
        return roleMapper.deleteById(id)>=0;
    }

    @Override
    public List<RoleEntity> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public List<RoleEntity> getRoles(int userId) {
        List<UserRoleEntity> roleEntityList = userRoleMapper.findByUserId(userId);
        List<Integer> roles = roleEntityList.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());
        if(roles != null && roles.size()>0) {
            return roleMapper.selectBatchIds(roles);
        }else{
            return null;
        }
    }

    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<RoleEntity> createWrapper(RoleEntity entity) {
        LambdaQueryWrapper<RoleEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }

        if (!StringUtils.isEmpty(entity.getId()) && entity.getId() > 0) {
            wrappers.eq(RoleEntity::getId, entity.getId());
        }

        if (!StringUtils.isEmpty(entity.getCode())) {
            wrappers.eq(RoleEntity::getCode, entity.getCode());
        }

        if (!StringUtils.isEmpty(entity.getName())) {
            wrappers.eq(RoleEntity::getName, entity.getName());
        }

        return wrappers;
    }
}
