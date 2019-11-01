package com.fh.shop.api.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static Long hincrBy(String key, String field, Long value) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            Long hincr = jedis.hincrBy(key, field, value);
            return hincr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {

        }
    }

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
     * @param field
     * @return
     */
    public static String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            String value = jedis.hget(key, field);
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
     * @param field
     * @param value
     */
    public static void hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jedis.close();
        }
    }

    /**
     * @param key
     * @param field
     */
    public static void hdel(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.hdel(key, field);
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
    public static Long del(String key) {
        Jedis jedis = null;
        Long del;
        try {
            jedis = RedisPool.getResource();
            del = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
        return del;
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

    public static boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jedis.close();
        }
    }
}
