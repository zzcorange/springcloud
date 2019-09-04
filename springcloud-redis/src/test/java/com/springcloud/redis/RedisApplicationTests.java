package com.springcloud.redis;

import com.springcloud.redis.entity.User;
import com.springcloud.redis.util.RedisUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {
    @Autowired
    private  RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;
    @Test
    @Ignore
    public void contextLoads() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("a","bbb");
        System.out.println(operations.get("a"));
    }
    @Test
    public void testExp(){
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set("cc","bbb",1);
        System.out.println(operations.get("cc"));
    }
    @Test
    public void entity(){
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        User user = new User();
        user.setUserName("tom");
        user.setPassword("tompassword");
        operations.set("tomer3","121212",20000);
    }
    @Test
    public void getEntity(){
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        //User user = (User)operations.get("tomer2");
//        User user = new User();
//        user.setUserName("tom");
//        user.setPassword("tompassword");
//        operations.set("232",user);
//        operations.set("test1","2");
        System.out.println(">>>>>>>>>>>>"+operations.get("test1"));
     //   System.out.println("---------------------------------->"+((User)operations.get("232")).getPassword());
    }
    @Test
    public void testlist(){
      //  redisUtil.insert("abcdc","b",10000);
       // redisUtil.delete("abc");
        System.out.println(">>>>>>>>>>>>>>"+((User)(redisUtil.select("232"))).getPassword());
    }
    @Test
    public void testhash(){
        User user = new User();
        user.setPassword("tomer");
        user.setUserName("tim");
        redisUtil.insertHash("user",user.getUserName(),user);
        Map test = redisUtil.getEntry("user");
        System.out.print("-------------------->");
        Set<String> a = test.keySet();
        for(String temp :a){
            System.out.println(temp+"="+((User)test.get(temp)).getPassword());
        }
    }
    //序列化
    public static byte [] serialize(Object obj){
        ObjectOutputStream obi=null;
        ByteArrayOutputStream bai=null;
        try {
            bai=new ByteArrayOutputStream();
            obi=new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt=bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //反序列化
    public static Object unserizlize(byte[] byt){
        ObjectInputStream oii=null;
        ByteArrayInputStream bis=null;
        bis=new ByteArrayInputStream(byt);
        try {
            oii=new ObjectInputStream(bis);
            Object obj=oii.readObject();
            return obj;
        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;
    }
    /**
     * list test
     */
    @Test
    public void testList(){
        redisUtil.insertListLeft("testlist","temp");
        System.out.println("分割线");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+   redisUtil.getListLeft("testlist"));
    }


    /**
     * 测试 set
     */
    @Test
    public void testSet(){
        User user=  new User();
        user.setUserName("tomser");
        user.setPassword("tomuserpassword");
        redisUtil.insertSet("userSet",user);
        System.out.println("华丽丽的分割线");
        User ans = (User)redisUtil.selectSet("userSet");
        System.out.println(ans.getPassword());
    }
    /**
     * 测试用户5分钟内不允许登录5次
     */
    @Test
    public void testIntter(){
        System.out.println( redisUtil.insertZset("zset","王三1狗",11));
        System.out.println(redisUtil.insertZset("zset","王四1狗",12));
        Set<Object> set = redisUtil.rangeByScore("zset",11,12.01);
        System.out.println("set的长度："+set.size());
        for(Object s :set){
            System.out.println(s.toString());
        }
    }

}
