package com.xinbo.chainblock.controller.admin;

import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.admin.UserRoleEntity;
import com.xinbo.chainblock.service.UserRoleService;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userRoleController")
@RequestMapping("/admin/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @Operation(summary = "insertBatch", description = "批量新增用户角色")
    @Transactional
    @PostMapping("insertBatch/{userId}")
    public R<Object> insertBatch(@PathVariable int userId, @RequestBody List<Integer> roles){
        boolean isDeleteSuccess= userRoleService.deleteByUser(userId);
        boolean isInsertSuccess=true;
        UserRoleEntity userRoleEntity=UserRoleEntity.builder().build();
        for(Integer role : roles){
            userRoleEntity.setRoleId(role);
            userRoleEntity.setUserId(userId);
            isInsertSuccess = userRoleService.insert(userRoleEntity);
            if(isInsertSuccess == false){
                break;
            }
        }

        return R.builder().code(isDeleteSuccess && isInsertSuccess ? StatusCode.SUCCESS : StatusCode.FAILURE).build();
    }
}
