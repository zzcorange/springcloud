package com.zzc.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.AntPathMatcher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class SecurityApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println( new BCryptPasswordEncoder(4).encode("password"));;
    }
    @Test
    public void test(){
        String value = "/js/jquery-3.4.1.min.js";
        String match = "/**/*.js";
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        System.out.println(antPathMatcher.match(match,value));
    }
    @Test
    public void testLamba(){
        List<String> list = Arrays.asList("1","2","3","4");
        list.forEach((string)->{
            System.out.println(string);
            return ;
        });
    }
    @Test
    public void testEncode() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("用户名或密码不正确","utf-8"));;
    }
    @Test
    public void testPage(){
        int pageBegin = 10;
        int pageEnd=20;
        int pageShow=3;//假定展示前3后三，比如 1,2,3 。。。 8，9,10
        if(pageEnd-pageBegin<=pageShow*2){//数量不够，需要全展示
            for(int i=pageBegin;i<=pageEnd;i++)
                System.out.print(i+",");
        }else{//可以有隐藏的空间
            for(int i=0;i<pageShow;i++)  // 从pageBegin 开始展示三个页码
                System.out.print((pageBegin+i)+",");
            System.out.print("...");//展示省略号
            for(int i=pageShow-1;i>=0;i--)// 从pageEnd结束 展示三个页码
                System.out.print((pageEnd-i)+",");
        }
    }
}
