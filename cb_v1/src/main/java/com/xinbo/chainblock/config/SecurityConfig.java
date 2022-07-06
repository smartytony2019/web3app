package com.xinbo.chainblock.config;

import com.xinbo.chainblock.consts.GlobalConst;
import com.xinbo.chainblock.utils.JwtUser;
import com.xinbo.chainblock.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 熊二
 * @date 2021/2/5 15:49
 * @desc file desc
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 使用 Spring Security 推荐的加密方式进行登录密码的加密
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 放行swagger所有请求
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/v3/api-docs/**")
                .antMatchers("/api/**")
                .antMatchers("/i18n/**")
                .antMatchers("/test/**")
                .antMatchers("/content/**")
                .antMatchers("/webjars/springfox-swagger-ui/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/swagger-ui/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                // 指定路径下的资源需要进行验证后才能访问
                .antMatchers("/").permitAll()
                // 配置登录地址
                .antMatchers(HttpMethod.POST, GlobalConst.ADMIN_LOGIN).permitAll()
                // 其他请求需验证
                .anyRequest().authenticated()
                .and()
                .apply(securityConfigurationAdapter());
    }

    private JwtConfig securityConfigurationAdapter() throws Exception {
        return new JwtConfig(new JwtAuthorizationFilter(authenticationManager()));
    }


    class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

        private JwtAuthorizationFilter jwtAuthorizationFilter;

        public JwtConfig(JwtAuthorizationFilter jwtAuthorizationFilter) {
            this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        }

        @Override
        public void configure(HttpSecurity http) {
            http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        }

    }


    class JwtAuthorizationFilter extends BasicAuthenticationFilter {

        public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
            super(authenticationManager);
        }

        @Override
        protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
            // 从 HTTP 请求中获取 token
            String token = this.getTokenFromHttpRequest(request);
            // 验证 token 是否有效
            if (StringUtils.hasText(token) && JwtUtil.validateToken(token)) {
                // 获取认证信息
                JwtUser jwtUser = JwtUtil.parseToken(token);

                //生成认证
                List<SimpleGrantedAuthority> authorities =
                        ObjectUtils.isEmpty(jwtUser.getAuthority()) ? Collections.singletonList(new SimpleGrantedAuthority("admin")) :
                                jwtUser.getAuthority().stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList());
                Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, token, authorities);

                // 将认证信息存入 Spring 安全上下文中
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // 放行请求
            filterChain.doFilter(request, response);
        }

        /**
         * 从 HTTP 请求中获取 token
         *
         * @param request HTTP 请求
         * @return 返回 token
         */
        private String getTokenFromHttpRequest(HttpServletRequest request) {
            String authorization = request.getHeader(GlobalConst.TOKEN_HEADER);
            if (authorization == null || !authorization.startsWith(GlobalConst.TOKEN_PREFIX)) {
                return null;
            }
            // 去掉 token 前缀
            return authorization.replace(GlobalConst.TOKEN_PREFIX, "");
        }

    }

}
