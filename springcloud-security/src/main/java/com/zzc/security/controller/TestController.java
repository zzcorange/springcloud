package com.zzc.security.controller;

import com.alibaba.fastjson.JSON;
import com.zzc.security.config.AppFilterInvocationSecurityMetadataSource;
import com.zzc.security.config.WebSecurityConfigAdapterImpl;
import com.zzc.security.dao.RoleDao;
import com.zzc.security.dao.UrlDao;
import com.zzc.security.entity.Role;
import com.zzc.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * @Author 张真诚
 * @Date 2019/9/24
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private RoleDao roleDao;
    @GetMapping("/getAllRole")
    public String getAllRole(){
        return JSON.toJSONString(sessionRegistry.getAllPrincipals());
    }
    @GetMapping("/getAllRole1")
    public String getAllRole1(){


        return JSON.toJSONString(SecurityContextHolder.getContext());
    }
    @GetMapping("/addRole")
    public String addRole(){
       User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       user.getAuthorities().add(Role.builder().authority("ROLE_ADMIN").build());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
       Collection collections =  authentication.getAuthorities();
//       collections.add(Role.builder().authority("ROLE_ADMIN").build());

       return "success";
    }
    @GetMapping("/admin")
    public String admin(HttpSession session){
        String name ="user";
        Role role = Role.builder().authority("ROLE_ADMIN").build();
        System.out.println("sessionRegistry>>>>"+JSON.toJSONString(sessionRegistry));
        System.out.println("getAllPrincipals>>>>"+JSON.toJSONString(sessionRegistry.getAllPrincipals()));
        for(Object o:sessionRegistry.getAllPrincipals()){
            System.out.println("遍历sesesion:"+JSON.toJSONString(sessionRegistry.getAllSessions(o,false
            )));
        }
        for(Object o : sessionRegistry.getAllPrincipals()){
            User user = (User)o;
            if(name.equals(user.getUsername())){

                System.out.println("getT="+JSON.toJSONString(sessionRegistry.getAllPrincipals().contains(o)));
                System.out.println("getAllSession==="+JSON.toJSONString(sessionRegistry.getAllSessions(o,false)));

                for(SessionInformation sessionInformation :sessionRegistry.getAllSessions(o,false)){
                    System.out.println("sessionId>>>>>"+sessionInformation.getSessionId()
                    );
                    user.getAuthorities().add(role);
//                    sessionRegistry.refreshLastRequest();
                    sessionInformation.refreshLastRequest();
                }
                break;
            }
        }

        return "success";
    }
    @GetMapping("/logout")
    public String logout(@RequestParam String username){
        if(StringUtils.isEmpty(username))
            return "用户是空的";
        for(Object o: sessionRegistry.getAllPrincipals()){
            User user = (User)o;
            if(username.equals(user.getUsername())){
                sessionRegistry.getAllSessions(o,false).forEach((session)->{
                    session.expireNow();
                });
            }
        }

        return "success";
    }

    @GetMapping("/getRoles")
    public String getRoles(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return "权限信息为："+JSON.toJSONString(roleDao.getRole(user.getUsername()));
    }

}


