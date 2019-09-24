package com.zzc.security.controller;

import com.zzc.security.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 张真诚
 * @Date 2019/9/16
 */
@Controller

public class JspController {
    @RequestMapping("/login")
    public String login(Model model){
        System.out.println("come to login mapping ");
        model.addAttribute("name", "world");
        return "login";
    }
    @RequestMapping("/index")
    public String index(ModelMap map){
        map.put("itdragonStr", "itdragonBlog");
        map.put("itdragonBool", true);
        map.put("itdragonArray", new Integer[]{1,2,3,4});
        map.put("itdragonList", Arrays.asList(1,3,2,4,0));
        Map itdragonMap = new HashMap();
        itdragonMap.put("thName", "${#...}");
        itdragonMap.put("desc", "变量表达式内置方法");
        map.put("itdragonMap", itdragonMap);
        map.put("itdragonDate", new Date());
        map.put("itdragonNum", 888.888D);
        map.put("list",Arrays.asList(0,1,2,3,4,5,6,7,8,9,10));
        return "index";
    }
}
