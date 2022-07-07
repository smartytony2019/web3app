package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.dto.PermissionDto;
import com.xinbo.chainblock.entity.StatisticsEntity;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.entity.admin.RolePermissionEntity;
import com.xinbo.chainblock.entity.admin.UserEntity;
import com.xinbo.chainblock.entity.admin.UserRoleEntity;
import com.xinbo.chainblock.mapper.PermissionMapper;
import com.xinbo.chainblock.mapper.RolePermissionMapper;
import com.xinbo.chainblock.mapper.UserMapper;
import com.xinbo.chainblock.mapper.UserRoleMapper;
import com.xinbo.chainblock.service.UserRoleService;
import com.xinbo.chainblock.service.UserService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionEntity> findPermission(int userId) {

        //Step 1: 根据用户id获取到角色
        List<UserRoleEntity> roleEntityList = userRoleMapper.findByUserId(userId);

        //Step 2: 根据角色获取到权限
        List<Integer> roles = roleEntityList.stream().map(UserRoleEntity::getRoleId).collect(Collectors.toList());
        List<RolePermissionEntity> rolePermissionEntityList = rolePermissionMapper.findByRoles(roles);

        List<Integer> permissions = rolePermissionEntityList.stream().map(RolePermissionEntity::getPermissionId).collect(Collectors.toList());

        return permissionMapper.findByIds(permissions);
    }


    public List<PermissionEntity> menu(int userId) {

        List<PermissionEntity> all = permissionMapper.findAll();
        List<PermissionEntity> permission = this.findPermission(userId);


        Set<Integer> set = new HashSet<>();
        for(PermissionEntity entity : permission) {
            String[] split = StringUtils.split(entity.getPath(), ",");
            if(ObjectUtils.isEmpty(split) || split.length <= 0) {
                continue;
            }

            Set<Integer> collect = Stream.of(split).map(Integer::parseInt).collect(Collectors.toSet());
            set.addAll(collect);
        }

        List<PermissionEntity> list = new ArrayList<>();
        for(Integer s : set) {
            PermissionEntity entity = all.stream().filter(f -> f.getId().equals(s)).findFirst().orElse(null);
            if(ObjectUtils.isEmpty(entity)) {
                continue;
            }
            list.add(entity);
        }

        List<PermissionEntity> collect = list.stream().filter(f -> f.getLevel() == 0).collect(Collectors.toList());
        for(PermissionEntity c : collect) {
            PermissionDto dto = MapperUtil.to(c, PermissionDto.class);
        }

        return null;
    }


    private PermissionDto handle(List<PermissionEntity> all, PermissionDto dto) {

        List<PermissionEntity> list = all.stream().filter(f -> f.getParentId().equals(dto.getId())).collect(Collectors.toList());

//        dto.setChild(list);

        return dto;
    }

}
