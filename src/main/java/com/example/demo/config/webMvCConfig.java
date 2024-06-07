package com.example.demo.config;


import com.example.demo.interceptios.Logininterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration

public class webMvCConfig implements WebMvcConfigurer {
    @Autowired
    private Logininterceptor logininterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:D:\\javakaifagongju\\图片\\");
        registry.addResourceHandler("/cover/**").addResourceLocations("file:D:\\javakaifagongju\\封面地址\\");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logininterceptor).excludePathPatterns("/login","/hello","/img/*","/hello1","/cover","/shopsee","/hello111","/cover/*");

    }

}