package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.annotation.RequiredPermission;
import com.xinbo.chainblock.bo.BasePageBo;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.RoleDto;
import com.xinbo.chainblock.entity.admin.RoleEntity;
import com.xinbo.chainblock.enums.PermissionCodeEnum;
import com.xinbo.chainblock.service.RolePermissionService;
import com.xinbo.chainblock.service.RoleService;
import com.xinbo.chainblock.service.UserRoleService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.RoleVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("RoleController")
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserRoleService userRoleService;

    @Operation(summary = "findPage", description = "角色列表")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody RoleVo vo, @PathVariable long current, @PathVariable long size) {
        RoleEntity entity = MapperUtil.to(vo, RoleEntity.class);
        BasePageBo basePageBo = roleService.findPage(entity,current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePageBo).build();
    }

    @Operation(summary = "insert", description = "新增角色")
    @RequiredPermission(PermissionCodeEnum.role_add)
    @PostMapping("insert")
    public R<Object> insert(@RequestBody RoleVo vo){
        RoleEntity entity = MapperUtil.to(vo, RoleEntity.class);
        boolean isSuccess = roleService.insert(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "update", description = "修改角色")
    @RequiredPermission(PermissionCodeEnum.role_edit)
    @PostMapping("update")
    public R<Object> update(@RequestBody RoleVo vo){
        RoleEntity entity = MapperUtil.to(vo, RoleEntity.class);
        boolean isSuccess = roleService.update(entity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "find", description = "查找单条记录")
    @PostMapping("find/{id}")
    public R<Object> find(@PathVariable int id) {
        RoleEntity entity = roleService.find(id);
        return R.builder().code(StatusCode.SUCCESS).data(entity).build();
    }

    @Operation(summary = "delete", description = "删除")
    @RequiredPermission(PermissionCodeEnum.role_del)
    @Transactional
    @PostMapping("delete/{roleId}")
    public R<Object> delete(@PathVariable int roleId){
      boolean isSuccess =  rolePermissionService.deleteByRole(roleId) &&  userRoleService.deleteByRole(roleId) && roleService.delete(roleId);
      return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "getRoles", description = "获取所有角色和用户自己的角色")
    @GetMapping("getRoles/{userId}")
    public R<Object> getRoles(@PathVariable int userId) {
        List<RoleEntity> ownRoles=roleService.getRoles(userId);
        List<RoleEntity> allRoles=roleService.findAll();
        RoleDto roleDto=RoleDto.builder().ownRoles(ownRoles).allRoles(allRoles).build();
        return R.builder().code(StatusCode.SUCCESS).data(roleDto).build();
    }

}
