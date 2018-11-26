package com.ks.shiro;

import org.apache.shiro.cache.Cache;

import com.ks.top.redis.RedisManager;

public class JedisShiroCacheManager implements ShiroCacheManager{

	private RedisManager redisManager;  
	  
    public RedisManager getRedisManager() {  
        return redisManager;  
    }  
  
    public void setRedisManager(RedisManager redisManager) {  
        this.redisManager = redisManager;  
    }  
  
    @Override  
    public <K, V> Cache<K, V> getCache(String name) {
        return new JedisShiroCache<K, V>(redisManager, name);  
    }  
  
    @Override  
    public void destroy() {  
        redisManager.init();  
        redisManager.flushDB();  
    }

}
