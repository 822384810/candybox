package me.candybox.core.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis工具
 */
@Component
public class RedisUtil {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;   

    /**
     * 判断是否存在key
     * @param key
     * @return
     */
    public  boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * redis中获取值
     * @param key
     * @return
     */
    public Object get(String key){
        return  redisTemplate.opsForValue().get(key);
    }
    

    /**
     * redis插入值
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key,Object value){
        boolean result = false;
        try{
             redisTemplate.opsForValue().set(key,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    /**
     * 设置过期时间，单位秒
     * @param key
     * @param timeOut
     * @return
     */
    public boolean expireSeconds(String key,long timeOut){
        return redisTemplate.expire(key,timeOut,TimeUnit.SECONDS);
    }
}
