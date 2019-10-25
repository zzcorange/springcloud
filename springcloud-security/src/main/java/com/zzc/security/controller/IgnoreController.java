package com.zzc.security.controller;

import com.zzc.security.config.AppFilterInvocationSecurityMetadataSource;
import com.zzc.security.config.RoleBasedVoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 张真诚
 * @Date 2019/10/25
 */
@RestController
@RequestMapping("/ignore")
public class IgnoreController {
    @RequestMapping("/addRole")
    public String addRole(){
        AppFilterInvocationSecurityMetadataSource.urlRoleMap.put("/hello","ROLE_USER");
        RoleBasedVoter.urlRoleMap.put("/hello**","ROLE_USER");
        return "SUCCESS";
    }
}
