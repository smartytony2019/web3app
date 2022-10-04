package com.xinbo.chainblock.controller.admin;


import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.consts.RoleTypeConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.dto.PermissionDto;
import com.xinbo.chainblock.dto.UserDto;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.entity.admin.UserEntity;
import com.xinbo.chainblock.enums.LanguageEnum;
import com.xinbo.chainblock.enums.SymbolEnum;
import com.xinbo.chainblock.service.UserService;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("adminIndex")
@RequestMapping("/admin")
public class IndexController {

    @Autowired
    private UserService userService;

    @JwtIgnore
    @Operation(summary = "login", description = "后端登录")
    @PostMapping("login")
    public R<Object> login() {
        //Step4: 生成token
        JwtUserBo jwtUserBo = JwtUserBo.builder()
                .uid(1)
                .username("admin")
                .build();
        String token = JwtUtil.generateToken(jwtUserBo);

        JwtUserBo jwtUserBo1 = JwtUtil.parseToken(token);

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return R.builder().code(StatusCode.SUCCESS).data(map).build();
    }


    @Operation(summary = "userInfo", description = "会员信息")
    @GetMapping("userInfo")
    public R<Object> userInfo() {
        JwtUserBo jwtUserBo = JwtUtil.getJwtUser();
        UserEntity entity = userService.findById(jwtUserBo.getUid());
        UserDto dto = MapperUtil.to(entity, UserDto.class);
        return R.builder().code(StatusCode.SUCCESS).data(dto).build();
    }

    @Operation(summary = "menu", description = "菜单")
    @GetMapping("menu")
    public R<Object> menu() {
        JwtUserBo jwtUserBo = JwtUtil.getJwtUser();
        List<PermissionDto> list=null;
        if(jwtUserBo.getRoleType() == RoleTypeConst.NORMAL) {
            list = userService.allMenu(jwtUserBo.getUid());
        }

        if(jwtUserBo.getRoleType() == RoleTypeConst.ADMINISTRATOR){
            list=userService.superAdminMenu();
        }
        return R.builder().code(StatusCode.SUCCESS).data(list.get(0).getChildren()).build();
    }

    /**
     *
     * 获取除了按钮外的整个菜单树
     * @return
     */
    @Operation(summary = "menuExcludeBtn", description = "除了按钮外的菜单树")
    @GetMapping("menuExcludeBtn")
    public R<Object> menuExcludeBtn() {
        JwtUserBo jwtUserBo = JwtUtil.getJwtUser();
        List<PermissionEntity> list = userService.AllMenuExcludeButton(jwtUserBo.getUid());
        return R.builder().code(StatusCode.SUCCESS).data(list).build();
    }

    @Operation(summary = "symbol", description = "币种")
    @PostMapping("symbol")
    public R<Object> symbol() {
        return R.builder().code(StatusCode.SUCCESS).data(SymbolEnum.toList()).build();
    }


    @Operation(summary = "language", description = "语言")
    @PostMapping("language")
    public R<Object> language() {
        return R.builder().code(StatusCode.SUCCESS).data(LanguageEnum.toList()).build();
    }

}
