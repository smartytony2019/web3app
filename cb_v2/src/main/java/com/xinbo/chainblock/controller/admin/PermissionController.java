package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.PermissionDto;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.service.PermissionService;
import com.xinbo.chainblock.service.RolePermissionService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.PermissionVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("permissionController")
@RequestMapping("/admin/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Operation(summary = "find", description = "查找单条记录")
    @PostMapping("find/{id}")
    public R<Object> find(@PathVariable int id) {
        PermissionEntity entity = permissionService.find(id);
        entity.setParentName(permissionService.find(entity.getParentId()).getNameDefault());
        return R.builder().code(StatusCode.SUCCESS).data(entity).build();
    }

    @Operation(summary = "insert", description = "新增权限")
    @PostMapping("insert")
    public R<Object> insert(@RequestBody PermissionVo vo){
        PermissionEntity permissionEntity= MapperUtil.to(vo,PermissionEntity.class);
        PermissionEntity entity=permissionService.find(permissionEntity.getParentId());
        boolean flag=true;
        StringBuilder parentPath=new StringBuilder();
        parentPath.append(permissionEntity.getParentId());
        while(flag){
            if(entity!=null){
                parentPath.append(",").append(entity.getParentId());
                entity=permissionService.find(entity.getParentId());
            }else{
                flag=false;
            }
        }
        permissionEntity.setParentPath(parentPath.toString());
        boolean isSuccess=permissionService.insert(permissionEntity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "update", description = "更新菜单")
    @PostMapping("update")
    public R<Object> update(@RequestBody PermissionVo vo){
        PermissionEntity permissionEntity = MapperUtil.to(vo, PermissionEntity.class);
        PermissionEntity entity=permissionService.find(permissionEntity.getParentId());
        boolean flag=true;
        StringBuilder parentPath=new StringBuilder();
        parentPath.append(permissionEntity.getParentId());
        while(flag){
            if(entity!=null){
                parentPath.append(",").append(entity.getParentId());
                entity=permissionService.find(entity.getParentId());
            }else{
                flag=false;
            }
        }
        permissionEntity.setParentPath(parentPath.toString());
        boolean isSuccess = permissionService.update(permissionEntity);
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "delete", description = "删除权限")
    @Transactional
    @PostMapping("delete/{id}")
    public R<Object> delete(@PathVariable int id){
        boolean isSuccess;
        PermissionEntity entity=new PermissionEntity();
        entity.setId(id);
        boolean flag = permissionService.findByParentId(entity);
        if(flag){
            return R.builder().code(StatusCode.FAILURE).msg("该菜单权限存在子集关联，不允许删除").build();
        }else{
            isSuccess=permissionService.delete(id)&&rolePermissionService.deleteByPermission(id);
        }
        return R.builder().code(isSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

    @Operation(summary = "menu", description = "菜单树")
    @GetMapping("menu")
    public R<Object> menu(){
        return R.builder().code(StatusCode.SUCCESS).data(permissionService.allMenu()).build();
    }

    /**
     *
     * 获取除了按钮外的整个菜单树
     * @return
     */

    @Operation(summary = "menuExcludeBtn", description = "除了按钮外的菜单树")
    @GetMapping("menuExcludeBtn")
    public R<Object> menuExcludeBtn() {
        return R.builder().code(StatusCode.SUCCESS).data(permissionService.AllMenuExcludeButton()).build();
    }

    @Operation(summary = "getMenu", description = "获取所有菜单和获取角色对应菜单")
    @GetMapping("getMenu/{roleId}")
    public R<Object> getMenu(@PathVariable int roleId) {
        List<PermissionEntity> ownPermissions=permissionService.getRolePermission(roleId);
        List<PermissionEntity> allPermissions=permissionService.allMenu();
        PermissionDto permissionDto=PermissionDto.builder().ownPermissions(ownPermissions).allPermissions(allPermissions).build();
        return R.builder().code(StatusCode.SUCCESS).data(permissionDto).build();
    }

}
