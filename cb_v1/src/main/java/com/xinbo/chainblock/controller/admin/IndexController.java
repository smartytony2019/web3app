package com.xinbo.chainblock.controller.admin;


import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.UserDto;
import com.xinbo.chainblock.entity.admin.UserEntity;
import com.xinbo.chainblock.service.UserService;
import com.xinbo.chainblock.utils.JwtUser;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private UserService userService;

    @Operation(summary = "login", description = "后端登录")
    @PostMapping("login")
    public R<Object> login() {
        List<String> authority = Arrays.asList("index:test");
        //Step4: 生成token
        JwtUser jwtUser = JwtUser.builder()
                .uid(1)
                .username("admin")
                .build();
        String token = JwtUtil.generateToken(jwtUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return R.builder().code(StatusCode.SUCCESS).data(map).build();
    }


    @Operation(summary = "userInfo", description = "会员信息")
    @GetMapping("userInfo")
    public R<Object> userInfo() {
        JwtUser jwtUser = JwtUtil.getJwtUser();
        UserEntity entity = userService.findById(jwtUser.getUid());
        UserDto dto = MapperUtil.to(entity, UserDto.class);
        dto.setPermissions(Arrays.asList("read:system", "write:system", "delete:system"));
        dto.setRoles(Arrays.asList("admin"));
        return R.builder().code(StatusCode.SUCCESS).data(dto).build();
    }

}
