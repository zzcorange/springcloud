package com.zzc.security.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author 张真诚
 * @Date 2019/9/17
 */
@Configuration
@Component
public class MyPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        System.out.println("o->"+o);
        System.out.println("o1->"+o1);
        System.out.println("authentication->"+ JSONObject.toJSONString(authentication));
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        System.out.println("o->"+o);
        System.out.println("s->"+s);
        System.out.println("authentication->"+ JSONObject.toJSONString(authentication));
        System.out.println("serializable->"+ JSONObject.toJSONString(serializable));
        return false;
    }
}
