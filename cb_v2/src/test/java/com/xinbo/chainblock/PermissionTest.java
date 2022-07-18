package com.xinbo.chainblock;

import com.alibaba.fastjson.JSON;
import com.xinbo.chainblock.dto.PermissionDto;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.enums.PermissionCodeEnum;
import com.xinbo.chainblock.service.*;
import com.xinbo.chainblock.utils.MapperUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

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

//        int userId = 1;
//        List<PermissionEntity> menu = userService.menu(userId);
//        List<PermissionDto> many = MapperUtil.many(menu, PermissionDto.class);
//        System.out.println(JSON.toJSONString(many));

        Map<Integer, String> integerStringMap = PermissionCodeEnum.toMap();
        System.out.println(integerStringMap);


    }
}
