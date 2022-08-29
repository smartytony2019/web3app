package com.xinbo.chainblock.interceptor;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xinbo.chainblock.annotation.JwtIgnore;
import com.xinbo.chainblock.annotation.RequiredPermission;
import com.xinbo.chainblock.consts.GlobalConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.service.UserService;
import com.xinbo.chainblock.bo.JwtUserBo;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }


        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if (this.jwtIgnore(handler)) {
            return true;
        }


        // 获取请求头信息authorization信息
        final String authHeader = request.getHeader(GlobalConst.TOKEN_HEADER);
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(GlobalConst.TOKEN_PREFIX)) {
            this.returnJson(response, R.builder().code(StatusCode.TOKEN_ERROR).msg("token error").build());
            return false;
        }

        // 获取token
        final String token = authHeader.substring(7);


        // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
        boolean verify = JwtUtil.validateToken(token);
        if(!verify) {
            this.returnJson(response, R.builder().code(StatusCode.TOKEN_ERROR).msg("token verify error").build());
            return false;
        }


        JwtUserBo jwtUserBo = JwtUtil.parseToken(token.replace(GlobalConst.TOKEN_PREFIX, ""));


        //判断是否有权限
        if (!this.hasPermission(handler, jwtUserBo)) {
            this.returnJson(response, R.builder().code(StatusCode.NOT_PERMISSION).msg("not permission").build());
            return false;
        }

        return true;
    }

    /**
     * 获取
     * @param handler
     * @param jwtUserBo
     * @return
     */
    private boolean hasPermission(Object handler, JwtUserBo jwtUserBo) {
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

            if (requiredPermission != null && !ObjectUtils.isEmpty(requiredPermission.value())) {
                List<Integer> permission = userService.findPermission(jwtUserBo.getUid());
                if (CollectionUtils.isEmpty(permission)) {
                    return false;
                }
                return permission.contains(requiredPermission.value().getCode());
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
