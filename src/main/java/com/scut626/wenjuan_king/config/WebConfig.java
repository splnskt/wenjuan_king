package com.scut626.wenjuan_king.config;

import com.scut626.wenjuan_king.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/pages/login.html",
                        "/pages/register.html",
                        "/user/**",
                        "/*",
                        "/css/**",
                        "/js/**",
                        "/paper/paper-lists",
                        "/paper/template-list");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/user_image/**").addResourceLocations("file:D:/1/WebProgram/wenjuan_king/src/main/resources/static/user_image/");
    }
}
