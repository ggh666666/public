package com.fh.shop.admin.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    private RedisPool() {
    }

    private static JedisPool jedisPool;

    /**
     * 初始化redis连接池配置
     */
    private static void initJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);//最大连接数
        jedisPoolConfig.setMinIdle(100);//最小空闲数
        jedisPoolConfig.setMaxIdle(100);//最大空闲数
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPool = new JedisPool(jedisPoolConfig, "192.168.214.128", 7020);
        System.out.println();
//        jedisPool = new JedisPool(jedisPoolConfig, "192.168.133.128", 7020);
    }

    static {
        initJedisPool();
    }

    /**
     * 获取redis连接
     *
     * @return
     */
    public static Jedis getResource() {
        return jedisPool.getResource();
    }
}
