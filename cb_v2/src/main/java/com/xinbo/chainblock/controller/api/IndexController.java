package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.SystemConfigEntity;
import com.xinbo.chainblock.service.SystemConfigService;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tony
 * @date 8/30/22 7:57 下午
 * @desc file desc
 */
@RestController("ApiIndexController")
@RequestMapping("api/index")
public class IndexController {

    @Autowired
    private SystemConfigService systemConfigService;


    @JwtIgnore
    @Operation(summary = "config", description = "系统配置")
    @PostMapping("config")
    public R<Object> config() {
        List<SystemConfigEntity> all = systemConfigService.findAll();
        return R.builder().data(StatusCode.SUCCESS).data(all).build();
    }


}
