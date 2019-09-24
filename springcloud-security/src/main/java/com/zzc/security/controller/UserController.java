package com.zzc.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 张真诚
 * @Date 2019/9/17
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/getAll")
    @PreAuthorize("hasPermission('1212','23232') or hasRole('ROLE_ADMIN111111')")
    public String getAll(){

        System.out.println("come to here");
        return "this is all";
    }
    @RequestMapping("/getAllFail")
    @PreAuthorize("hasRole('ROLE_ADMIN111111')")
    public String getAllFail(){

        System.out.println("come to here");
        return "this is all";
    }

}
