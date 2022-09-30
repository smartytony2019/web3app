package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.admin.RolePermissionEntity;
import com.xinbo.chainblock.service.RolePermissionService;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("rolePermissionController")
@RequestMapping("/admin/rolePermission")
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Operation(summary = "insertBatch", description = "批量角色权限")
    @Transactional
    @PostMapping("insertBatch/{roleId}")
    public R<Object> insertBatch(@PathVariable int roleId, @RequestBody List<Integer> permissions){
       boolean isDeleteSuccess= rolePermissionService.deleteByRole(roleId);
       boolean isInsertSuccess=true;
        RolePermissionEntity rolePermissionEntity=RolePermissionEntity.builder().build();
        for(Integer permission : permissions){
            rolePermissionEntity.setPermissionId(permission);
            rolePermissionEntity.setRoleId(roleId);
            isInsertSuccess = rolePermissionService.insert(rolePermissionEntity);
            if(isInsertSuccess == false){
                break;
            }
        }

        return R.builder().code(isDeleteSuccess && isInsertSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }

}
