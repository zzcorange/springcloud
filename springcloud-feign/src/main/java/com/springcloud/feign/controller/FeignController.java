package com.springcloud.feign.controller;

import com.springcloud.feign.service.SchedualServiceHitest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {
    @Autowired
    private SchedualServiceHitest schedualServiceHitest;
    @RequestMapping(value="hi",method = RequestMethod.GET)
    public String feignHi(@RequestParam String name){
        return schedualServiceHitest.sayHi(name)+" from feignHi";
    }

}
