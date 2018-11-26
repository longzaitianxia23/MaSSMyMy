package com.ks.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class CustomShiroCacheManager implements CacheManager{
	
	private ShiroCacheManager shrioCacheManager;

	public ShiroCacheManager getShrioCacheManager() {  
        return shrioCacheManager;  
    }  
  
    public void setShrioCacheManager(ShiroCacheManager shrioCacheManager) {  
        this.shrioCacheManager = shrioCacheManager;  
    }  
  
    @Override  
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {  
        return getShrioCacheManager().getCache(name);  
    }
}
