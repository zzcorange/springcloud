package com.zzc.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityMetadataSourceAdvisor;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @Author 张真诚
 * @Date 2019/9/6
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{
    /**
     * Mapped to ResourceHttpRequestHandler ["classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/", "/"]
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        registry.addResourceHandler("/jsp/**").addResourceLocations("classpath:/static/jsp/");
        registry.addResourceHandler("/jsp/**").addResourceLocations("classpath:/static/jsp/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

//        registry.addResourceHandler("*.jpg").addResourceLocations("classpath:/static/img");

    }
}
