package com.zzc.dubboproducter;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations= {"classpath:dubbo-demo-consumer.xml"})
public class DubboProducterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProducterApplication.class, args);
    }
}
