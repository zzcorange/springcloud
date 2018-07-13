package com.springcloud.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class, args);
    }
    @RequestMapping("/")
    public String home() {
        return "Hello Docker World."
                + "<br />Welcome to <a href='http://waylau.com'>waylau.com</a></li>";
    }

}
