package com.zzc.dubbocosumer.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zzc.dubboapi.test.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Comsumer {
    @Reference
    private TestService testService;
    @RequestMapping("/test")
    public String test(@RequestParam String name){
        return testService.sayHello(name);
    }
}
