package com.springcloud.zipkinclientmiya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
public class ZipkinclientMiyaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinclientMiyaApplication.class, args);
    }

    private static final Logger log = Logger.getLogger(ZipkinclientMiyaApplication.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/hi")
    public String home(){
        log.log(Level.INFO,"hi is being called in miya");
        return "hi, i  am  miya";
    }

    @RequestMapping("/miya")
    public String info(){
        log.log(Level.INFO,"info is being called in miya");
        return restTemplate.getForObject("http://localhost:8988/info",String.class);
    }
    @Bean
    public RestTemplate getRestTemplate(){
         return new RestTemplate();
    }

}
