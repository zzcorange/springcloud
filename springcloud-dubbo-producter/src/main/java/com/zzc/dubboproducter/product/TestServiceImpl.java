package com.zzc.dubboproducter.product;

import com.alibaba.dubbo.config.annotation.Service;
import com.zzc.dubboapi.test.TestService;
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String sayHello(String name) {
        return "hello"+" name ; from springcloud";
    }
}
