package com.zzc.security.config;

import com.alibaba.fastjson.JSON;
import com.zzc.security.controller.ConfigController;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 张真诚
 * @Date 2019/9/25
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler{
    private AntPathMatcher antPathMatcher=new AntPathMatcher();
    private final String json = "{\"code\":403,\"message\":\"无权访问，请联系管理员\"}";
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    private  Map<String,Boolean> returnTypeMap = new HashMap<>();
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

            if(returnTypeMap.size()==0) returnTypeMap= ConfigController.returnTypeMap;;
            String url =  httpServletRequest.getRequestURI();
            System.out.println("请求链接是："+url);
             boolean isStringReturn = false;
        System.out.println(JSON.toJSONString(returnTypeMap));
             for(String key :returnTypeMap.keySet()){
                 if(antPathMatcher.match(key,url)){
                     isStringReturn = returnTypeMap.get(key);
                    break;
                 }
             }
             if(isStringReturn){
                 httpServletResponse.setStatus(200);
                 httpServletResponse.setContentType("application/json");
                 httpServletResponse.setCharacterEncoding("utf-8");
                 httpServletResponse.getOutputStream().write(json.getBytes());

             }else{
                 httpServletResponse.sendRedirect("/error?accept=forbidden");
             }
    }


}
