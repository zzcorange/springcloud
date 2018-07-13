package com.springcloud.zipkinclienthi;

import brave.sampler.Sampler;
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
public class ZipkinclientHiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinclientHiApplication.class, args);
    }
    private static final Logger Log = Logger.getLogger(ZipkinclientHiApplication.class.getName());
    @Autowired
    private RestTemplate restTemplate;
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();

    }
    @RequestMapping(value="/hi")
    public String callHome(){
        Log.log(Level.INFO,"calling trace service-hi  in hi ");
        return restTemplate.getForObject("http://localhost:8989/miya",String.class);

    }
    @RequestMapping(value="/info")
    public String info(){
            Log.log(Level.INFO,"calling trace service-hi in info");
            return "i am service-hi";
    }
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
