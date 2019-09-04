package com.zzc.dubbocosumer;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:dubbo-demo-consumer.xml"})
@DubboComponentScan(basePackages = "com.zzc.dubbocosumer.consumer")
public class DubboCosumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboCosumerApplication.class, args);
    }
}
