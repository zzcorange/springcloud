package com.zzc.security.config;

import com.alibaba.fastjson.JSON;
import com.zzc.security.dao.RoleDao;
import com.zzc.security.dao.UrlDao;
import com.zzc.security.entity.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author 张真诚
 * @Date 2019/9/18
// */

public class AppFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private FilterInvocationSecurityMetadataSource superMetadataSource;

    private UrlDao urlDao;


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return superMetadataSource.getAllConfigAttributes();
    }

    public AppFilterInvocationSecurityMetadataSource(FilterInvocationSecurityMetadataSource expressionBasedFilterInvocationSecurityMetadataSource, UrlDao urlDao){
        this.superMetadataSource = expressionBasedFilterInvocationSecurityMetadataSource;
        this.urlDao = urlDao;
        List<Url> urlList = urlDao.queryAll();

        if(urlList==null||urlList.size()==0){
            System.out.println("权限配置为空，请检查");
            System.exit(0);
        }



        // TODO 从数据库加载权限配置
    }

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    // 这里的需要从DB加载
    private  Map<String,String> urlRoleMap = new HashMap<String,String>();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
        ArrayList<String> tempValue = new ArrayList<>();
        for(Map.Entry<String,String> entry:urlRoleMap.entrySet()){
            if(antPathMatcher.match(entry.getKey(),url)){

                tempValue.add(entry.getValue());

            }
        }
        //  返回代码定义的默认配置
        if(tempValue.size()>0){
            System.out.println(url+"拉取的权限数据："+JSON.toJSONString(tempValue.toArray( new String[tempValue.size()])));
            return SecurityConfig.createList(tempValue.toArray( new String[tempValue.size()]));
        }
        return superMetadataSource.getAttributes(object);
    }



    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}