package com.youngzz1k.weblog.web.utils;

import cn.hutool.core.util.BooleanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class nxLock {

    @Autowired
    private  StringRedisTemplate redisTemplate;


    public boolean tryLock(String key){
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, "1", 30L, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(aBoolean);
    }

    public void unLock(String key){
        redisTemplate.delete(key);
    }
}
