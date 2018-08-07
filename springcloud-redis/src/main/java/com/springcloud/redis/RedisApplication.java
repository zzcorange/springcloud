package com.springcloud.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RedisApplication {
    @Autowired
    protected RedisTemplate redisTemplate;
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
    @RequestMapping("/")
    public String test(){

        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("a","bbb");
        return operations.get("a");
    }
}
