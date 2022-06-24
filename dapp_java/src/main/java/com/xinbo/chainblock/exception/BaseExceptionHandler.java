package com.xinbo.chainblock.exception;

import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * @author 熊二
 * @date 2021/2/7 21:50
 * @desc 全局异常拦截器
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R<String> error(Exception ex) {

        String msg;
        if(ex instanceof MethodArgumentNotValidException) {
            msg = ((MethodArgumentNotValidException)ex).getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if(ex instanceof BindException) {
            msg = ((BindException)ex).getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if(ex instanceof ConstraintViolationException) {
            msg = ((ConstraintViolationException)ex).getConstraintViolations().iterator().next().getMessage();
        } else {
            msg = ex.getMessage();
        }

        log.error("全局异常拦截器捕获内容：{}", ex.getMessage());
        return new R<>(StatusCode.EXCEPTION, msg, "处理异常");
    }
}