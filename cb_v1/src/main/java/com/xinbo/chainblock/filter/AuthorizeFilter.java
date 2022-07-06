package com.xinbo.chainblock.filter;


import com.alibaba.fastjson.JSON;
import com.xinbo.chainblock.consts.GlobalConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.utils.JwtUser;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author 熊二
 * @date 2021/1/31 15:33
 * @desc file desc
 */
@Component
@Slf4j
public class AuthorizeFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.获取请求对象
        if (HttpMethod.resolve(request.getMethod()) == HttpMethod.OPTIONS) {
            response.setStatus(HttpStatus.OK.value());
            return;
        }

        //4.1 从头header中获取令牌数据
        String token = request.getHeaders(GlobalConst.TOKEN_HEADER).toString();

        //5 解析令牌数据 ( 判断解析是否正确,正确 就放行 ,否则 结束)
        try {
            if(!StringUtils.isEmpty(token)){
                JwtUser jwtUser = JwtUtil.parseToken(token.replace(GlobalConst.TOKEN_PREFIX, ""));

//                //Step1: TTL
//                String key = MessageFormat.format(RedisConst.ADMIN_JWT_KEY, RoleTypeEnum.Admin.getKey(), jwtUser.getUid());
//                if (redisTemplate.hasKey(key)) {
//                    redisTemplate.expire(key, 30, TimeUnit.MINUTES);
//                }
            }
            //***************** 放入到redis中并设置TTL
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            R<Object> resultResponse = R.builder()
                    .code(StatusCode.UNAUTHORIZED)
                    .msg("token不合法")
                    .build();

            response.setContentType("application/json;charset=utf-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JSON.toJSONString(resultResponse));
            printWriter.flush();
            printWriter.close();
            return;
        }
        filterChain.doFilter(request, response);
    }
}
