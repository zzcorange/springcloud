package com.zzc.security.controller;

import com.alibaba.fastjson.JSON;
import com.zzc.security.config.AppFilterInvocationSecurityMetadataSource;
import com.zzc.security.config.WebSecurityConfigAdapterImpl;
import com.zzc.security.entity.Role;
import com.zzc.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
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
    @GetMapping("/getAllRole1")
    public String getAllRole1(){
        return JSON.toJSONString(SecurityContextHolder.getContext());
    }
    @GetMapping("/admin")
    public String admin(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role role = Role.builder().authority("ROLE_ADMIN").build();
        System.out.println(role==null);
        System.out.println(user.getAuthorities()==null);
        user.getAuthorities().add(role);
        sessionRegistry.getAllPrincipals().add(user);

        return "success";
    }
}


