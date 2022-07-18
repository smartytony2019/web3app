package com.xinbo.chainblock.controller.admin;


import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.core.BasePage;
import com.xinbo.chainblock.dto.PermissionDto;
import com.xinbo.chainblock.dto.UserDto;
import com.xinbo.chainblock.entity.admin.PermissionEntity;
import com.xinbo.chainblock.entity.admin.UserEntity;
import com.xinbo.chainblock.service.UserService;
import com.xinbo.chainblock.utils.JwtUser;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.MapperUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController("adminUserController")
@RequestMapping("/admin/user")
public class UserController {

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
        map.put("token", String.format("Bearer %s", token));
        return R.builder().code(StatusCode.SUCCESS).data(map).build();
    }


    @Operation(summary = "find", description = "会员信息")
    @PostMapping("find")
    public R<Object> find() {
        JwtUser jwtUser = JwtUtil.getJwtUser();
        UserEntity entity = userService.findById(jwtUser.getUid());
        UserDto dto = MapperUtil.to(entity, UserDto.class);
        return R.builder().code(StatusCode.SUCCESS).data(dto).build();
    }

    @Operation(summary = "menu", description = "菜单")
    @GetMapping("menu")
    public R<Object> menu() {
        JwtUser jwtUser = JwtUtil.getJwtUser();
        List<PermissionEntity> list = userService.menu(jwtUser.getUid());

        List<PermissionDto> result = new ArrayList<>();
        for(PermissionEntity entity : list) {
            PermissionDto.Meta meta = PermissionDto.Meta.builder()
                    .title(entity.getTitle())
                    .icon(entity.getIcon())
                    .alwaysShow(true)
                    .build();
            PermissionDto dto = PermissionDto.builder()
                    .path(entity.getPath())
                    .component(entity.getComponent())
                    .redirect(entity.getRedirect())
                    .name(entity.getName())
                    .meta(meta)
                    .build();

            List<PermissionDto> childrenList = new ArrayList<>();
            List<PermissionEntity> children = entity.getChildren();
            for (PermissionEntity c : children) {
                PermissionDto.Meta childrenMeta = PermissionDto.Meta.builder()
                        .title(c.getTitle())
                        .noCache(true)
                        .build();
                PermissionDto childrenDto = PermissionDto.builder()
                        .path(c.getPath())
                        .component(c.getComponent())
                        .name(c.getName())
                        .meta(childrenMeta)
                        .build();
                childrenList.add(childrenDto);
            }
            dto.setChildren(childrenList);

            result.add(dto);
        }

        return R.builder().code(StatusCode.SUCCESS).data(result).build();
    }


    @Operation(summary = "findPage", description = "会员列表")
    @PostMapping("findPage/{current}/{size}")
    public R<Object> findPage(@RequestBody UserVo vo, @PathVariable long current, @PathVariable long size) {
        UserEntity entity = MapperUtil.to(vo, UserEntity.class);
        BasePage basePage = userService.findPage(entity, current, size);
        return R.builder().code(StatusCode.SUCCESS).data(basePage).build();
    }


    @Operation(summary = "findById", description = "查询会员")
    @GetMapping("findById/{id}")
    public R<Object> findById(@PathVariable Integer id) {
        return R.builder().code(StatusCode.SUCCESS).data(MapperUtil.to(userService.findById(id), UserDto.class)).build();
    }

}
