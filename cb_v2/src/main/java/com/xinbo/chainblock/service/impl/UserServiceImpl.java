package com.xinbo.chainblock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.dto.PermissionDto;
import com.xinbo.chainblock.dto.UserDto;
import com.xinbo.chainblock.entity.admin.*;
import com.xinbo.chainblock.mapper.*;
import com.xinbo.chainblock.service.UserService;
import com.xinbo.chainblock.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
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

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<Integer> findPermission(int userId) {
        //根据用户id拿到权限
        List<PermissionEntity> permission = this.getPermission(userId);
        return permission.stream().map(PermissionEntity::getCode).distinct().collect(Collectors.toList());
    }

    private List<PermissionEntity> getPermission(int userId) {
        //Step 1: 根据用户id获取到角色
        List<UserRoleEntity> roleEntityList = userRoleMapper.findByUserId(userId);

        //Step 2: 根据角色获取到权限
        List<Integer> roles = roleEntityList.stream().
                filter(item->{
                    if(item != null){
                      RoleEntity entity= roleMapper.selectById(item.getRoleId());
                      return entity.getIsDelete(); //返回未禁用的角色
                    }
                    return true;
                }).
                map(UserRoleEntity::getRoleId).collect(Collectors.toList());
        List<RolePermissionEntity> rolePermissionEntityList = rolePermissionMapper.findByRoles(roles);

       List<Integer>  permissions = rolePermissionEntityList.stream().
                filter(item->{
                    if(item != null){
                        PermissionEntity entity =permissionMapper.selectById(item.getPermissionId());
                        return entity.getIsDelete()==1; //返回未禁用的权限
                    }
                    return true;
                }).
                 map(RolePermissionEntity::getPermissionId).distinct().collect(Collectors.toList());

        return permissionMapper.findByIds(permissions);

    }




    @Override
    public List<PermissionEntity> menu(int userId) {
        //根据用户id拿到权限
        List<PermissionEntity> permission = this.getPermission(userId);

        //根据权限表path获取父级id
        List<Integer> parentIdList = new ArrayList<>();
        for (PermissionEntity entity : permission) {
            String[] split = StringUtils.split(entity.getParentPath(), ",");
            if (ObjectUtils.isEmpty(split) || split.length <= 0) {
                continue;
            }

            List<Integer> collect = Stream.of(split).map(Integer::parseInt).collect(Collectors.toList());
            parentIdList.addAll(collect);
        }
        List<Integer> ids = parentIdList.stream().distinct().collect(Collectors.toList());

        //根据id查询
        List<PermissionEntity> entityList = permissionMapper.findByIds(ids);

        //绑定层级
        List<PermissionEntity> folders = entityList.stream().filter(f -> f.getParentId().equals(0)).collect(Collectors.toList());
        for(PermissionEntity folder : folders) {
            List<PermissionEntity> pages = entityList.stream().filter(f -> f.getParentId().equals(folder.getId())).collect(Collectors.toList());
            folder.setChildren(pages);
        }
        return folders;
    }




    @Override
    public UserEntity findById(int userId) {
        return userMapper.selectById(userId);
    }


    @Override
    public BasePageBo findPage(UserEntity entity, long current, long size) {
        Page<UserEntity> page = new Page<>(current, size);
//        page.addOrder(OrderItem.asc("create_time"));
        IPage<UserEntity> iPage = userMapper.selectPage(page, this.createWrapper(entity));
        return BasePageBo.builder().total(iPage.getTotal()).records(MapperUtil.many(iPage.getRecords(), UserDto.class)).build();
    }



    /**
     * 创建查询条件
     *
     * @param entity 实体
     * @return LambdaQueryWrapper
     */
    private LambdaQueryWrapper<UserEntity> createWrapper(UserEntity entity) {
        LambdaQueryWrapper<UserEntity> wrappers = Wrappers.lambdaQuery();
        if (ObjectUtils.isEmpty(entity)) {
            return wrappers;
        }
        if (!StringUtils.isEmpty(entity.getId()) && entity.getId() > 0) {
            wrappers.eq(UserEntity::getId, entity.getId());
        }
        if (!StringUtils.isEmpty(entity.getUsername())) {
            wrappers.eq(UserEntity::getUsername, entity.getUsername());
        }
        return wrappers;
    }

    /**
     * 整个菜单树
     *
     * @param userId
     * @return
     */

    @Override
    public List<PermissionDto> allMenu(int userId){
        List<PermissionEntity> permission = this.getPermission(userId).stream()
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
        List<PermissionDto> menuList=new ArrayList<>();
        rootPermission.stream().forEach(item->{

            PermissionDto.Meta meta = PermissionDto.Meta.builder()
                    .title(item.getTitle())
                    .icon(item.getIcon())
                    .alwaysShow(true)
                    .build();
            PermissionDto dto =PermissionDto.builder()
                    .path(item.getPath())
                    .component(item.getComponent())
                    .redirect(item.getRedirect())
                    .name(item.getName())
                    .meta(meta)
                    .sort(item.getSort())
                    .id(item.getId())
                    .build();
            dto.setChildren(subMenuDto(permission,dto.getId()));
            menuList.add(dto);
        });

        return menuList;
    }

    /**
     * 除了按钮外的整个菜单树
     * @param userId
     * @return
     */
    @Override
    public List<PermissionEntity> AllMenuExcludeButton(int userId) {
        List<PermissionEntity> permission = this.getPermission(userId).stream()
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
            rootItem.setChildren(subMenu(permission,rootItem.getId()));
            menuList.add(rootItem);
        });

        return menuList;
    }

    @Override
    public boolean update(UserEntity entity) {
        return userMapper.updateById(entity)>0;
    }

    @Override
    public boolean delete(int id) {
        return userMapper.deleteById(id)>0;
    }

    @Override
    public boolean isUserNameExist(String userName) {
      UserEntity entity = userMapper.findByUserName(userName);
      if(ObjectUtils.isEmpty(entity)){
          return true;
      }else{
          return false;
      }

    }

    @Override
    public boolean insert(UserEntity entity) {
        return userMapper.insert(entity)>0;
    }


    /**
     * 遍历子菜单
     * @param allPermission
     * @param permissionId
     * @return
     */
    public List<PermissionDto> subMenuDto(List<PermissionEntity> allPermission,int permissionId){
        List<PermissionDto> children=new ArrayList<>();
        allPermission.stream().forEach(item->{
            if(permissionId==item.getParentId()){
                PermissionDto.Meta meta = PermissionDto.Meta.builder()
                        .title(item.getTitle())
                        .icon(item.getIcon())
                        .alwaysShow(true)
                        .build();
                PermissionDto dto =PermissionDto.builder()
                        .path(item.getPath())
                        .component(item.getComponent())
                        .redirect(item.getRedirect())
                        .name(item.getName())
                        .meta(meta)
                        .sort(item.getSort())
                        .id(item.getId())
                        .build();
                children.add(dto);
            }
        });
        Collections.sort(children,(obj1,obj2)->obj1.getSort()-obj2.getSort());
        children.stream().forEach(item->{
            item.setChildren(subMenuDto(allPermission,item.getId()));
        });
        return children;
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
