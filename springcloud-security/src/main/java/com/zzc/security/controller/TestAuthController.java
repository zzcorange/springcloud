package com.zzc.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author 张真诚
 * @Date 2019/9/25
 */
@Controller
@RequestMapping("/testAuth")
public class TestAuthController {
    @GetMapping("/getDate")
    @ResponseBody
    public String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "当前时间是："+dateFormat.format(new Date());
    }
    @GetMapping("/index")
    public String index(){
        System.out.println();
        return "index";
    }
}
