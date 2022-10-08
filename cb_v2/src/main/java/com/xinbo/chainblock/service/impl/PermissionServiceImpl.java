package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.entity.admin.BannerEntity;
import com.xinbo.chainblock.entity.admin.NoticeEntity;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.entity.admin.RolePermissionEntity;
import com.xinbo.chainblock.mapper.PermissionMapper;
import com.xinbo.chainblock.mapper.RolePermissionMapper;
import com.xinbo.chainblock.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tony
 * @date 6/24/22 4:31 下午
 * @desc file desc
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public boolean insert(PermissionEntity entity) {
        return permissionMapper.insert(entity)>0;
    }

    @Override
    public boolean update(PermissionEntity entity) {
        return permissionMapper.updateById(entity)>0;
    }

    @Override
    public List<PermissionEntity> findAll() {
        return permissionMapper.findAll();
    }

    @Override
    public PermissionEntity find(int id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public boolean delete(int id) {
        return permissionMapper.deleteById(id)>0;
    }

    @Override
    public boolean findByParentId(PermissionEntity entity){
        List<PermissionEntity> permissionEntityList = permissionMapper.selectList(this.createWrapper(entity));
        return permissionEntityList.size()>0;
    }

    @Override
    public List<PermissionEntity> allMenu(){
        List<PermissionEntity> permission = this.findAll().stream()
                .map(item->{
                    if(item!=null&&item.getParentId()!=0){
                        String parentName=permissionMapper.selectById(item.getParentId()).getNameDefault();
                        item.setParentName(parentName);
                    }
                    return item;
                })
                .collect(Collectors.toList());

        //获取最顶层的菜单
        List<PermissionEntity> rootPermission=permission.stream().filter(item->0==item.getParentId())
                .collect(Collectors.toList());
        List<PermissionEntity> menuList=new ArrayList<>();
        rootPermission.stream().forEach(rootItem->{
            rootItem.setChildren(this.subMenu(permission,rootItem.getId()));
            menuList.add(rootItem);
        });

        return menuList;
    }

    /**
     *
     * 除了按钮外的所有菜单
     * @return
     */
    @Override
    public List<PermissionEntity> AllMenuExcludeButton() {

        List<PermissionEntity> permission = this.findAll().stream()
                .map(item->{
                    if(item!=null&&item.getParentId()!=0){
                        String parentName=permissionMapper.selectById(item.getParentId()).getNameDefault();
                        item.setParentName(parentName);
                    }
                    return item;
                })
                .filter(item->{
                    if(item!=null && item.getNodeType()!=null){
                        return item.getNodeType()!=3;
                    }
                    return true;
                }).collect(Collectors.toList());

        //获取最顶层的菜单
        List<PermissionEntity> rootPermission=permission.stream().filter(item->0==item.getParentId())
                .collect(Collectors.toList());
        List<PermissionEntity> menuList=new ArrayList<>();
        rootPermission.stream().forEach(rootItem->{
            rootItem.setChildren(this.subMenu(permission,rootItem.getId()));
            menuList.add(rootItem);
        });

        return menuList;
    }

    /**
     * 获取对应角色的权限
     * @param roleId
     * @return
     */

    @Override
    public List<PermissionEntity> roleMenu(int roleId) {

        List<PermissionEntity> permissionEntityList = this.getRolePermission(roleId).stream()
                .map(item->{
                    if(item!=null&&item.getParentId()!=0){
                        String parentName=permissionMapper.selectById(item.getParentId()).getNameDefault();
                        item.setParentName(parentName);
                    }
                    return item;
                })
                .collect(Collectors.toList());

        //获取最顶层的菜单
        List<PermissionEntity> rootPermission=permissionEntityList.stream().filter(item->0==item.getParentId())
                .collect(Collectors.toList());

        List<PermissionEntity> menuList=new ArrayList<>();

        rootPermission.stream().forEach(rootItem->{
            rootItem.setChildren(this.subMenu(permissionEntityList,rootItem.getId()));
            menuList.add(rootItem);
        });

        return menuList;
    }


    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<PermissionEntity> createWrapper(PermissionEntity entity) {
        LambdaQueryWrapper<PermissionEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }

        if (!StringUtils.isEmpty(entity.getId())) {
            wrappers.eq(PermissionEntity::getParentId, entity.getId());
        }

        return wrappers;
    }

    /**
     * 获取对应的角色权限
     * @param roleId
     * @return
     */

    @Override
    public List<PermissionEntity> getRolePermission(int roleId){
        List<RolePermissionEntity> rolePermissionEntityList = rolePermissionMapper.findByRoleId(roleId);
        List<Integer> permissions = rolePermissionEntityList.stream().map(RolePermissionEntity::getPermissionId).distinct().collect(Collectors.toList());
        if(permissions != null && permissions.size()>0) {
            return permissionMapper.findByIds(permissions);
        }else{
            return null;
        }
    }

    @Override
    public List<PermissionEntity> findByIds(List<Integer> ids) {
        return permissionMapper.findByIds(ids);
    }

    @Override
    public BasePageBo findPageByParentId(int parentId,long current,long size) {
        Page<PermissionEntity> page = new Page<>(current, size);
        IPage<PermissionEntity> iPage = permissionMapper.selectByParentId(page,parentId);
        List<PermissionEntity> permissionEntityList = iPage.getRecords();
        return BasePageBo.builder().total(iPage.getTotal()).records(permissionEntityList).build();
    }

    /**
     * 遍历子菜单
     * @param allPermission
     * @param permissionId
     * @return
     */
    public List<PermissionEntity> subMenu(List<PermissionEntity> allPermission,int permissionId){
        List<PermissionEntity> children=new ArrayList<>();
        allPermission.stream().forEach(item->{
            if(permissionId==item.getParentId()){
                children.add(item);
            }
        });
        Collections.sort(children,(obj1, obj2)->obj1.getSort()-obj2.getSort());
        children.stream().forEach(item->{
            item.setChildren(subMenu(allPermission,item.getId()));
        });
        return children;
    }

}
