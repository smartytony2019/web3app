package com.xinbo.chainblock;

import com.xinbo.chainblock.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tony
 * @date 7/7/22 4:12 下午
 * @desc file desc
 */

@RunWith(SpringRunner.class)
@ActiveProfiles("company")
@SpringBootTest
public class PermissionTest {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionService permissionService;



    @Test
    public void test01() {

        int userId = 1;

        userService.findPermission(userId);

        System.out.println(111);

    }

}
