package me.candybox.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import me.candybox.core.interceptor.AccessTokenInterceptor;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    @Autowired
    private AccessTokenInterceptor accessTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessTokenInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
    
}
