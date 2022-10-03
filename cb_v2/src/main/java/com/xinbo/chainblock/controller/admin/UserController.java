package com.xinbo.chainblock.controller.admin;


import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.PermissionDto;
import com.xinbo.chainblock.dto.UserDto;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.entity.admin.RoleEntity;
import com.xinbo.chainblock.entity.admin.RolePermissionEntity;
import com.xinbo.chainblock.entity.admin.UserEntity;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.PermissionVo;
import com.xinbo.chainblock.vo.RolePermissionVo;
import com.xinbo.chainblock.vo.RoleVo;
import com.xinbo.chainblock.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController("adminUserController")
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserRoleService userRoleService;

    @Operation(summary = "login", description = "后端登录")
    @PostMapping("login")
    public R<Object> login() {
        List<String> authority = Arrays.asList("index:test");
        //Step4: 生成token
        JwtUserBo jwtUserBo = JwtUserBo.builder()
                .uid(1)
                .username("admin")
                .build();
        String token = JwtUtil.generateToken(jwtUserBo);
        Map<String, String> map = new HashMap<>();
        map.put("token", String.format("Bearer %s", token));
        return R.builder().code(StatusCode.SUCCESS).data(map).build();
    }


    @Operation(summary = "find", description = "会员信息")
    @PostMapping("find")
    public R<Object> find() {
        JwtUserBo jwtUserBo = JwtUtil.getJwtUser();
        UserEntity entity = userService.findById(jwtUserBo.getUid());
        UserDto dto = MapperUtil.to(entity, UserDto.class);
        return R.builder().code(StatusCode.SUCCESS).data(dto).build();
    }



    @Operation(summary = "menu", description = "菜单")
    @GetMapping("menu")
    public R<Object> menu() {
        JwtUserBo jwtUserBo = JwtUtil.getJwtUser();
        List<PermissionEntity> list = userService.menu(jwtUserBo.getUid());

        List<PermissionDto> result = new ArrayList<>();
        for (PermissionEntity entity : list) {
            PermissionDto.Meta meta = PermissionDto.Meta.builder()
                    .title(entity.getTitle())
                    .icon(entity.getIcon())
                    .alwaysShow(true)
                    .build();
            PermissionDto dto = PermissionDto.builder()
                    .path(entity.getPath())
                    .component(entity.getComponent())
                    .redirect(entity.getRedirect())
                    .name(entity.getName())
                    .meta(meta)
                    .build();

            List<PermissionDto> childrenList = new ArrayList<>();
            List<PermissionEntity> children = entity.getChildren();
            for (PermissionEntity c : children) {
                PermissionDto.Meta childrenMeta = PermissionDto.Meta.builder()
                        .title(c.getTitle())
                        .noCache(true)
                        .build();
                PermissionDto childrenDto = PermissionDto.builder()
                        .path(c.getPath())
                        .component(c.getComponent())
                        .name(c.getName())
                        .meta(childrenMeta)
                        .build();
                childrenList.add(childrenDto);
            }
            dto.setChildren(childrenList);

            result.add(dto);
        }

        return R.builder().code(StatusCode.SUCCESS).data(result).build();
    }


    @Operation(summary = "findPage", description = "会员列表")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody UserVo vo, @PathVariable long current, @PathVariable long size) {
        UserEntity entity = MapperUtil.to(vo, UserEntity.class);
        BasePageBo basePageBo = userService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }


    @Operation(summary = "findById", description = "查询会员")
    @GetMapping("findById/{id}")
    public R<Object> findById(@PathVariable Integer id) {
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(userService.findById(id), UserDto.class)).build();
    }

    @Operation(summary = "insertRole", description = "新增角色")
    @PostMapping("insertRole")
    public R<Object> insertRole(@RequestBody RoleVo vo) {
        RoleEntity entity = MapperUtil.to(vo, RoleEntity.class);
        entity.setIsDelete(false);
        boolean isSuccess = roleService.insert(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "updateRole", description = "更新角色")
    @PostMapping("updateRole")
    public R<Object> updateRole(@RequestBody RoleVo vo) {
        RoleEntity entity = MapperUtil.to(vo, RoleEntity.class);
        boolean isSuccess = roleService.update(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "deleteRole", description = "删除角色")
    @Transactional
    @PostMapping("deleteRole/{id}")
    public R<Object> deleteRole(@PathVariable Integer id) {
        RoleEntity entity = new RoleEntity();
        entity.setId(id);
        entity.setIsDelete(true);
        boolean isSuccess = roleService.update(entity) && userRoleService.deleteByRole(id) && rolePermissionService.deleteByRole(id);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "insertPermission", description = "新增权限")
    @PostMapping("insertPermission")
    public R<Object> insertPermission(@RequestBody PermissionVo vo) {
        PermissionEntity entity = MapperUtil.to(vo, PermissionEntity.class);
        entity.setIsDelete(true);
        boolean isSuccess = permissionService.insert(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "updatePermission", description = "更新权限")
    @PostMapping("updatePermission")
    public R<Object> updatePermission(@RequestBody PermissionVo vo) {
        PermissionEntity entity = MapperUtil.to(vo, PermissionEntity.class);
        boolean isSuccess = permissionService.update(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "deletePermission", description = "删除权限")
    @Transactional
    @PostMapping("deletePermission/{id}")
    public R<Object> deletePermission(@PathVariable Integer id) {
        PermissionEntity entity = new PermissionEntity();
        entity.setId(id);
        entity.setIsDelete(false);
        boolean isSuccess = permissionService.update(entity) && rolePermissionService.deleteByPermission(id);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "insertRolePermission", description = "新增角色的权限")
    @PostMapping("insertRolePermission")
    public R<Object> insertRolePermission(@RequestBody RolePermissionVo vo) {
        RolePermissionEntity entity = MapperUtil.to(vo, RolePermissionEntity.class);
        boolean isSuccess = rolePermissionService.insert(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "updateRolePermission", description = "更新角色的权限")
    @PostMapping("updateRolePermission")
    public R<Object> updateRolePermission(@RequestBody RolePermissionVo vo) {
        RolePermissionEntity entity = MapperUtil.to(vo, RolePermissionEntity.class);
        boolean isSuccess = rolePermissionService.update(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    /**
     * 更新用户信息
     * @param vo
     * @return
     */

    @Operation(summary = "update", description = "更新用户信息")
    @PostMapping("update")
    public R<Object> update(@RequestBody UserVo vo){
        UserEntity entity = MapperUtil.to(vo, UserEntity.class);
        boolean isSuccess = userService.update(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Operation(summary = "delete", description = "删除用户")
    @PostMapping("delete/{id}")
    public R<Object> delete(@PathVariable Integer id){
        boolean isSuccess=userService.delete(id);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    /**
     * 添加用户
     * @param vo
     * @return
     */
    @Operation(summary = "insert", description = "添加用户")
    @PostMapping("insert")
    public R<Object> insert(@RequestBody UserVo vo){
        UserEntity entity = MapperUtil.to(vo, UserEntity.class);
        boolean isUserNameExist = userService.isUserNameExist(entity.getUsername());
        if(!isUserNameExist){
            return R.builder().code(StatusCode.FAILURE).msg("用户名重复").build();
        }else{
            boolean isSuccess = userService.insert(entity);
            return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
        }
    }
}
