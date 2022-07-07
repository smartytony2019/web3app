package com.xinbo.chainblock.interceptor;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.annotation.RequiredPermission;
import com.xinbo.chainblock.consts.GlobalConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.service.AdminUserService;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Slf4j
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AdminUserService adminUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        //判断是否有权限
        if (!this.hasPermission(handler)) {
            this.returnJson(response, R.builder().code(StatusCode.NOT_PERMISSION).msg("not permission").build());
            return false;
        }


        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if (this.jwtIgnore(handler)) {
            return true;
        }


        // 获取请求头信息authorization信息
        final String authHeader = request.getHeader(GlobalConst.TOKEN_HEADER);
        log.info("## authHeader= {}", authHeader);

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(GlobalConst.TOKEN_PREFIX)) {
            log.info("### 用户未登录，请先登录 ###");
            this.returnJson(response, R.builder().code(StatusCode.TOKEN_ERROR).msg("token error").build());
            return false;
        }

        // 获取token
        final String token = authHeader.substring(7);


        // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
        JwtUtil.parseToken(token.replace(GlobalConst.TOKEN_PREFIX, ""));

        return true;
    }

    private boolean hasPermission(Object handler) {
        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            //方法上面注解
            RequiredPermission requiredPermission = method.getAnnotation(RequiredPermission.class);
            if (requiredPermission == null) {
                //类上面注解
                requiredPermission = method.getDeclaringClass().getAnnotation(RequiredPermission.class);
            }

            if (requiredPermission != null && StringUtils.isNotBlank(requiredPermission.value())) {
                List<String> permission = adminUserService.findPermission();
                if (CollectionUtils.isEmpty(permission)) {
                    return false;
                }
                return permission.contains(requiredPermission.value());
            }
        }

        return true;
    }

    private boolean jwtIgnore(Object handler) {
        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            JwtIgnore jwtIgnore = method.getAnnotation(JwtIgnore.class);
            if (jwtIgnore != null) {
                return true;
            }
        }
        return false;
    }


    private void returnJson(HttpServletResponse response, R<Object> r){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(r));
        } catch (IOException e){
            log.error("拦截器输出流异常"+e.getMessage());
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}
