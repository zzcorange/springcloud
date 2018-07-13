package com.springcloud.ribbon.service;

import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHiHystric implements  SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String anme){
        System.out.println("come here");
        return "sorry this is from sayHi";
    }
}
