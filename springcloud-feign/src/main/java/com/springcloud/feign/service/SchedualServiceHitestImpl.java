package com.springcloud.feign.service;

import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHitestImpl implements  SchedualServiceHitest{
    @Override
    public String sayHi(String name) {
        return "报错啦 from SchedualServiceHitestImpl";
    }
}
