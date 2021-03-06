package com.zzc.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Author 张真诚
 * @Date 2019/9/30
 */
@Configuration
@EnableWebMvc
public class CustomMVCConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        registry.addResourceHandler("/jsp/**").addResourceLocations("classpath:/static/jsp/");
        registry.addResourceHandler("/jsp/**").addResourceLocations("classpath:/static/jsp/");
        registry.addResourceHandler("/**.html").addResourceLocations("classpath:/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("*.jpg").addResourceLocations("classpath:/static/img");

    }
}
