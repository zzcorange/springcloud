package com.springcloud.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class RedisUtil {

        private RedisTemplate redisTemplate;
        private ValueOperations<String, Object> operations;
        private ValueOperations<String,String> stringOperations;


        private HashOperations hashOperations;
        private ListOperations  listOperations;
        private SetOperations setOperations;
        private GeoOperations geoOperations;
        private ZSetOperations zSetOperations;
        private ClusterOperations clusterOperations;



        private StringRedisTemplate stringRedisTemplate;


        @Autowired
        public RedisUtil(RedisTemplate redisTemplate,StringRedisTemplate stringRedisTemplate){
            this.redisTemplate = redisTemplate;
            this.operations = redisTemplate.opsForValue();
            this.hashOperations = redisTemplate.opsForHash();
            this.stringRedisTemplate = stringRedisTemplate;
            this.stringOperations = stringRedisTemplate.opsForValue();
            this.hashOperations = redisTemplate.opsForHash();
            this.listOperations = redisTemplate.opsForList();
            this.setOperations = redisTemplate.opsForSet();
            this.geoOperations = redisTemplate.opsForGeo();
            this.zSetOperations = redisTemplate.opsForZSet();
            this.clusterOperations = redisTemplate.opsForCluster();
        }
        public  void insert(String key,Object value,long exp){
            operations.set(key,value);
        }
        public  void insertString(String key,String value,long exp){
        operations.set(key,value);
    }
        public  Object  select(String key){
           return operations.get(key);
        }
        public void insertHash(String key,String hkey,Object hvalue){
            hashOperations.put(key,hkey,hvalue);
      }
        public Map getEntry(String key){
            return hashOperations.entries(key);
      }
        public long insertListLeft(String key,Object value){
            return  listOperations.leftPush(key,value);
        }
        public long insertListLeft(String key,Object value,long exp){
            return listOperations.leftPush(key,value,exp);
        }
        public Object getListLeft(String key){
            return listOperations.leftPop(key);
        }
        public long insertListRight(String key ,Object value){
             return listOperations.rightPush(key,value);
        }
        public long insertListRight(String key ,Object value,long exp){
           return  listOperations.rightPush(key,value,exp);
        }
        public Object getListRight(String key){
            return listOperations.rightPop(key);
        }


        public long  insertSet(String key,Object object){
            return setOperations.add(key,object);
        }
        public Object selectSet(String key){
            return setOperations.pop(key);
        }
    /**
     * 删除
     * @param key
     */
    public boolean  deleteString(String key){
          return   stringRedisTemplate.delete(key);
       }
     public boolean delete(String key){
          return   redisTemplate.delete(key);
     }
     public boolean insertZset(String key,Object value ,double score){
          return   zSetOperations.add(key,value,score);
     }
     public void deleteZset(String key,Object value){
        zSetOperations.remove(key,value);
     }
    public Set selectZSet(String key, long begin, long end){
       return   zSetOperations.range(key,begin,end);
    }
    public Set rangeByScore(String key, double begin, double end){
        return   zSetOperations.rangeByScore(key,begin,end);
    }
    public boolean getLock(String key,String value){
       return  stringOperations.setIfAbsent(key,value);
    }
}
