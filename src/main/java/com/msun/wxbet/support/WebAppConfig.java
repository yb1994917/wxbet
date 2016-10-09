/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.msun.wxbet.support.interceptor.RegisterInterceptor;
import com.msun.wxbet.support.interceptor.WechatAuthorizationInterceptor;
import com.msun.wxbet.support.interceptor.WechatJsApiInterceptor;

/**
 * @author zxc Feb 22, 2016 8:36:05 PM
 */
// @EnableRedisHttpSession
@Configuration
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("com.msun")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private RegisterInterceptor            registerInterceptor;
    @Autowired
    private WechatAuthorizationInterceptor wechatAuthorizationInterceptor;
    @Autowired
    private WechatJsApiInterceptor         wechatJsApiInterceptor;

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebAppConfig.class);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(wechatAuthorizationInterceptor).addPathPatterns("/bet/**", "/user/**");
        // registry.addInterceptor(registerInterceptor).addPathPatterns("");
        registry.addInterceptor(wechatJsApiInterceptor).addPathPatterns("/bet/**", "/user/**");
    }
}
