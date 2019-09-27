package com.zzc.security.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 张真诚
 * @Date 2019/9/26
 */
@RestController
@RequestMapping("/test")
@Component
public class ConfigController implements InitializingBean {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    public  static  Map<String,Boolean> returnTypeMap = new HashMap<>();
    private AntPathMatcher antPathMatcher;
    private void init(){
        System.out.println("初始化数据中");
        System.out.println(requestMappingHandlerMapping==null);
        Map<RequestMappingInfo, HandlerMethod> map =requestMappingHandlerMapping.getHandlerMethods();
        map.keySet().forEach((requestMappingInfo)->{
            HandlerMethod handlerMethod = map.get(requestMappingInfo);
            System.out.println(handlerMethod.getMethod().getDeclaringClass().getName()+">>>>>"+handlerMethod.getMethod().getName());
            boolean isStringReturn = null!=handlerMethod.getMethod().getAnnotation(ResponseBody.class)||null!=handlerMethod.getMethod().getDeclaringClass().getAnnotation(RestController.class);
            requestMappingInfo.getPatternsCondition().getPatterns().forEach((url)->{
                returnTypeMap.put(url,isStringReturn);
            });
        });

    }
    @GetMapping("/getData")
    public String getData(){
        return JSON.toJSONString(getReturnTypeMap());
    }
    @GetMapping("/getBean")
    public String getBean(){
        return "";
    }
    public   Map getReturnTypeMap(){
        if(returnTypeMap.size()==0) init();
        System.out.println("");
        return returnTypeMap;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
