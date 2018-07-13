package com.springcloud.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "service-lucy",fallback = SchedualServiceHitestImpl.class)
public interface SchedualServiceHitest {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/hi")
    String sayHi(@RequestParam(value = "name") String name);


}
