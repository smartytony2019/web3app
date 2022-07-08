package com.xinbo.chainblock.config;
import com.xinbo.chainblock.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


//    @Value("${interceptor.exclude.path:}")
//    private String[] excludePath;
//
//    @Bean
//    public SecurityInterceptor securityInterceptor() {
//        return new SecurityInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(securityInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePath);
//    }

    /**
     * 跨域支持
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD")
                .maxAge(3600 * 24);
    }
}