package com.fh.shop.admin.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {
    /**
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jedis.close();
        }
    }

    /**
     * @param key
     * @return
     */
    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            String value = jedis.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jedis.close();
        }
    }

    /**
     * @param key
     */
    public static void del(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
    }

    public static void del(String[] keys) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.del(keys);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
    }

    public static void expire(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
    }

    public static void setEx(String key, String value, Integer seconds) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jedis.close();
        }
    }
}
