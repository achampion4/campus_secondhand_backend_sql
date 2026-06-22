package com.hitwh.secondhand.config;

import com.hitwh.secondhand.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置：注册 JWT 拦截器并设置放行白名单
 * 负责人：董炜文  日期：6/17
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    public WebMvcConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")          // 拦截所有业务接口
                .excludePathPatterns(                  // 放行白名单
                        "/api/user/login",
                        "/api/user/register",
                        "/api/ping",
                        "/api/product/list",          // 商品列表游客可见
                        "/api/product/detail/**",
                        "/api/review/user/**",        // 卖家评价游客可见
                        "/api/ai/recommend"           // AI 导购游客可体验
                );
    }
}