package com.springcloud.servicehihystrix;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicehihystrixApplicationTests {

    @Test
    public void contextLoads() {
        String a="";
        String b=null;
   out(a);
   out(b);
    }
    public void out(String value){
        System.out.println(value.indexOf("已存在"));
    }
}
