package com.zzc.security.controller;

import com.alibaba.fastjson.JSON;
import com.zzc.security.config.AppFilterInvocationSecurityMetadataSource;
import com.zzc.security.config.WebSecurityConfigAdapterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author 张真诚
 * @Date 2019/9/24
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private SessionRegistry sessionRegistry;
    @GetMapping("/getAllRole")
    public String getAllRole(){
        return JSON.toJSONString(sessionRegistry.getAllPrincipals());
    }
}


