package com.zzc.security.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 张真诚
 * @Date 2019/9/5
 */
@RestController
@RequestMapping("/index")
@Configuration
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class AuthController {
    @PostMapping("/test")
    public String Sucess(){
        return "sucess";
    }
    @Secured("ROLE_USER")
    @GetMapping("/getNeedSecured")
    public String getNeedSecured(){
        return "this is need Secured";
    }
    @Secured("ROLE_TEST")
    @GetMapping("/getSecured")
    public String getSecured(){
        return "this is cannot secured";
    }
    @GetMapping("/data")
    public String data(){
        return "data";
    }
}
