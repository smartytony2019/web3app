package com.xinbo.chainblock.controller.admin;


import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.utils.JwtUser;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin/index")
public class IndexController {


    @Operation(summary = "login", description = "后端登录")
    @PostMapping("login")
    public R<Object> login() {
        List<String> authority = Arrays.asList("index:test");
        //Step4: 生成token
        JwtUser jwtUser = JwtUser.builder()
                .uid(1)
                .username("admin")
                .authority(authority)
                .build();
        String token = JwtUtil.generateToken(jwtUser);

        return R.builder().code(StatusCode.SUCCESS).data(token).build();
    }

    @Operation(summary = "getList", description = "测试")
    @PostMapping("getList")
    public R<Object> getList() {
        return R.builder().code(StatusCode.SUCCESS).data("success").build();
    }

    @Operation(summary = "test2", description = "测试")
    @PostMapping("test")
    public R<Object> test2() {
        return R.builder().code(StatusCode.SUCCESS).data("success").build();
    }

}
