package com.xinbo.chainblock.controller;

import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.service.UserService;
import com.xinbo.chainblock.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tony
 * @date 6/23/22 5:48 下午
 * @desc file desc
 */
@RestController
public class ApiController {

    @Autowired
    private UserService userService;

    @Operation(summary = "test")
    @GetMapping("test")
    public R<Object> test() {
        boolean isSuccess = userService.create();
        return R.builder().code(StatusCode.SUCCESS).data("111").build();
    }

    @Operation(summary = "test2")
    @GetMapping("test2")
    public R<Object> test2() {
        int a = 1 /0;
        return R.builder().code(StatusCode.SUCCESS).data(111).build();
    }



}
