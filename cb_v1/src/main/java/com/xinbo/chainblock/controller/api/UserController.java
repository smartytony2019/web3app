package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.entity.UserEntity;
import com.xinbo.chainblock.entity.UserFlowEntity;
import com.xinbo.chainblock.service.UserFlowService;
import com.xinbo.chainblock.service.UserService;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.RegisterVo;
import com.xinbo.chainblock.vo.UserFlowVo;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "register", description = "注册")
    @PostMapping("register")
    public R<Object> register(@RequestBody RegisterVo vo) {
        UserEntity entity = UserEntity.builder()
                .username(vo.getUsername())
                .pwd(vo.getPwd())
                .createTime(new Date())
                .version(1)
                .salt("1234")
                .money(0F)
                .build();

        boolean isSuccess = userService.register(entity, vo.getCode());
        if (isSuccess) {
            return R.builder().data(StatusCode.SUCCESS).build();
        } else {
            return R.builder().data(StatusCode.REGISTER_ERROR).build();
        }

    }

}
