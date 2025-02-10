package com.yilena.yilenastudysystem.webConfig;

import com.yilena.yilenastudysystem.Interceptor.YSSInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //自定义的拦截器对象
    @Autowired
    private YSSInterceptor ySSInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       //注册自定义拦截器对象
        registry.addInterceptor(ySSInterceptor).addPathPatterns("/**").excludePathPatterns("/login");//设置拦截器拦截的请求路径（ /** 表示拦截所有请求）
    }
}