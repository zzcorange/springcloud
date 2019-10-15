package com.zzc.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityMetadataSourceAdvisor;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Author 张真诚
 * @Date 2019/9/6
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurationSupport{

    /**
     * Mapped to ResourceHttpRequestHandler ["classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/", "/"]
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        registry.addResourceHandler("/jsp/**").addResourceLocations("classpath:/static/jsp/");
        registry.addResourceHandler("/jsp/**").addResourceLocations("classpath:/static/jsp/");
        registry.addResourceHandler("/**.html").addResourceLocations("classpath:/templates/");

//        registry.addResourceHandler("*.jpg").addResourceLocations("classpath:/static/img");

    }
}
