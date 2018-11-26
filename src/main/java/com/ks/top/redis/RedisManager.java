package com.ks.top.redis;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redismanager主要用来给用户提供一个设计完备的，通过jedis的jar包来管理redis内存数据库的各种方法
 * @author xushoushan
 *
 */
public class RedisManager {
	// ip和port属性都定义在了properties文件中，这里通过spring的注解方式来直接使用  
    @Value("${redis.ip}")
    private String host;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.maxActive}")
    private int maxActive;
    @Value("${redis.maxIdle}")
    private int maxIdle;
    @Value("${redis.maxWait}")
    private int maxWait;
  
    // 设置为0的话就是永远都不会过期  
    private int expire = 0;  
    
    // 定义一个管理池，所有的redisManager共同使用。  
    private static JedisPool jedisPool = null;
    
    public RedisManager() {  
    }  
  
    /** 
     *  
     * 初始化方法,在这个方法中通过host和port来初始化jedispool。 
     *  
     * */  
  
    public void init() {  
        if (null == host || 0 == port) {  
            System.out.println("请初始化redis配置文件");  
            throw new NullPointerException("找不到redis配置");  
        }  
        if (jedisPool == null) {
        	JedisPoolConfig config = new JedisPoolConfig();
        	//设置最大连接数
            //config.setMaxTotal(maxActive);
            //设置最大空闲数
            config.setMaxIdle(maxIdle);
            //设置超时时间
            config.setMaxWait(maxWait);
            jedisPool = new JedisPool(config, host, port);
        }  
    }  
  
    /** 
     * get value from redis 
     *  
     * @param key 
     * @return 
     */  
    public byte[] get(byte[] key) {  
        byte[] value = null;  
        Jedis jedis = jedisPool.getResource();  
        try {  
            value = jedis.get(key);  
        } finally {  
            jedisPool.returnResource(jedis);  
        }  
        return value;  
    }  
  
    /** 
     * get value from redis 
     * 通过keycongredis中取值
     * @param key 
     * @return 
     */  
//    public String get(String key) {  
//        String value = null;  
//        Jedis jedis = jedisPool.getResource();  
//        try {  
//            value = jedis.get(key);  
//        } finally {  
//            jedisPool.returnResource(jedis);  
//        }  
//        return value;  
//    }
    
    /**
     * 根据key 获取内容
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] value = jedis.get(key.getBytes());
            return SerializeUtil.unserialize(value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }
  
    /** 
     * set 
     *  
     * @param key 
     * @param value 
     * @return 
     */  
    public byte[] set(byte[] key, byte[] value) {  
        Jedis jedis = jedisPool.getResource();  
        try {  
            jedis.set(key, value);  
            if (this.expire != 0) {  
                jedis.expire(key, this.expire);  
            }  
        } finally {  
            jedisPool.returnResource(jedis);  
        }  
        return value;  
    }  
  
    /** 
     * set 
     *  
     * @param key 
     * @param value 
     * @return 
     */  
    public String set(String key, String value) {  
        Jedis jedis = jedisPool.getResource();  
        try {  
            jedis.set(key, value);  
            if (this.expire != 0) {  
                jedis.expire(key, this.expire);  
            }  
        } finally {  
            jedisPool.returnResource(jedis);  
        }  
        return value;  
    }  
  
    /** 
     * set 
     *  
     * @param key 
     * @param value 
     * @param expire 
     * @return 
     */  
    public byte[] set(byte[] key, byte[] value, int expire) {  
        Jedis jedis = jedisPool.getResource();  
        try {  
            jedis.set(key, value);  
            if (expire != 0) {  
                jedis.expire(key, expire);  
            }  
        } finally {  
            jedisPool.returnResource(jedis);  
        }  
        return value;  
    }
    
    /** 
     * set 
     *  
     * @param key 	缓存凭据
     * @param value 	缓存对象
     * @param expire  有效时间
     * @return 
     */  
    public boolean set(String key, Object value, int expire) {  
        Jedis jedis = jedisPool.getResource();  
        try {  
            jedis.set(key.getBytes(), SerializeUtil.serialize(value));
            if (expire != 0) {  
                jedis.expire(key.getBytes(), expire);  
            }
            return true;
        }catch(Exception e){
            return false;
        } finally {  
            jedisPool.returnResource(jedis);  
        }
    }
  
    /** 
     * set 
     *  
     * @param key 
     * @param value 
     * @param expire 
     * @return 
     */  
//    public String set(String key, String value, int expire) {  
//        Jedis jedis = jedisPool.getResource();  
//        try {  
//            jedis.set(key, value);  
//            if (expire != 0) {  
//                jedis.expire(key, expire);  
//            }  
//        } finally {  
//            jedisPool.returnResource(jedis);  
//        }  
//        return value;  
//    }  
  
    /** 
     * del 
     *  
     * @param key 
     */  
    public void del(byte[] key) {  
        Jedis jedis = jedisPool.getResource();  
        try {  
            jedis.del(key);  
        } finally {  
            jedisPool.returnResource(jedis);  
        }  
    }  
  
    /** 
     * del 
     *  
     * @param key 
     */  
    public void del(String key) {  
        Jedis jedis = jedisPool.getResource();  
        try {  
            jedis.del(key);  
        } finally {  
            jedisPool.returnResource(jedis);  
        }  
    }  
  
    /** 
     * flush 
     */  
    public void flushDB() {  
        Jedis jedis = jedisPool.getResource();  
        try {  
            jedis.flushDB();  
        } finally {  
            jedisPool.returnResource(jedis);  
        }  
    }  
  
    /** 
     * size 
     */  
    public Long dbSize() {  
        Long dbSize = 0L;  
        Jedis jedis = jedisPool.getResource();  
        try {  
            dbSize = jedis.dbSize();  
        } finally {  
            jedisPool.returnResource(jedis);  
        }  
        return dbSize;  
    }  
  
    /** 
     * keys 
     *  
     * @param regex 
     * @return 
     */  
    public Set<byte[]> keys(String pattern) {  
        Set<byte[]> keys = null;  
        Jedis jedis = jedisPool.getResource();  
        try {  
            keys = jedis.keys(pattern.getBytes());  
        } finally {  
            jedisPool.returnResource(jedis);  
        }  
        return keys;  
    }  
  
    public void dels(String pattern) {  
        Set<byte[]> keys = null;  
        Jedis jedis = jedisPool.getResource();  
        try {  
            keys = jedis.keys(pattern.getBytes());  
            Iterator<byte[]> ito = keys.iterator();  
            while (ito.hasNext()) {  
                jedis.del(ito.next());  
            }  
        } finally {  
            jedisPool.returnResource(jedis);  
        }  
    }  
  
    public String getHost() {  
        return host;  
    }  
  
    public void setHost(String host) {  
        this.host = host;  
    }  
  
    public int getPort() {  
        return port;  
    }  
  
    public void setPort(int port) {  
        this.port = port;  
    }  
  
    public int getExpire() {  
        return expire;  
    }  
  
    public void setExpire(int expire) {  
        this.expire = expire;  
    }

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	} 
    
}
