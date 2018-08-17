package kaizone.songmaya.smartns.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisUtil {
    private static Log logger = LogFactory.getLog(RedisUtil.class);
    private static final int D_DEFAULT_EXPIRE_TIME = 14400;
    private static final int D_MAX_TOTAL = 500;
    private static final int D_MAX_IDLE = 200;
    private static final int D_MIN_IDLE = 10;
    private static final int D_MAX_WAIT_MILLIS = 5000;
    private static int DEFAULT_EXPIRE_TIME;
    private static int MAX_TOTAL;
    private static int MAX_IDLE;
    private static int MIN_IDLE;
    private static int MAX_WAIT_MILLIS;
    private static String redisHost = "";
    private static int redisPort = 6379;
    private static JedisPool pool = null;

    public RedisUtil() {
    }

    private static synchronized JedisPool getPool() {
        DEFAULT_EXPIRE_TIME = StringUtils.isEmpty(CommonProperties.get("redis.default_expire_time")) ? 14400 : Integer.parseInt(CommonProperties.get("redis.default_expire_time").toString());
        MAX_TOTAL = StringUtils.isEmpty(CommonProperties.get("redis.max_total")) ? 500 : Integer.parseInt(CommonProperties.get("redis.max_total").toString());
        MAX_IDLE = StringUtils.isEmpty(CommonProperties.get("redis.max_idle")) ? 200 : Integer.parseInt(CommonProperties.get("redis.max_idle").toString());
        MIN_IDLE = StringUtils.isEmpty(CommonProperties.get("redis.min_idle")) ? 10 : Integer.parseInt(CommonProperties.get("redis.min_idle").toString());
        MAX_WAIT_MILLIS = StringUtils.isEmpty(CommonProperties.get("redis.max_wait_millis")) ? 5000 : Integer.parseInt(CommonProperties.get("redis.max_wait_millis").toString());
        if (pool == null) {
            redisHost =  CommonProperties.get("redis.host").toString();
            redisPort = Integer.parseInt(((String) CommonProperties.get("redis.port")).toString());
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis((long)MAX_WAIT_MILLIS);
            config.setMinIdle(MIN_IDLE);
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, redisHost, redisPort);
        }

        return pool;
    }

    public static synchronized void flush() {
        if (pool != null) {
            if (!pool.isClosed()) {
                pool.close();
            }

            pool = null;
        }

    }

    public static synchronized Jedis getJedis() {
        try {
            Jedis j = getPool().getResource();
            return j;
        } catch (Exception var2) {
            logger.error(String.format("获取Jedis实例异常: active:%d, idle:%d, waiters:%d. - %s", getNumActive(), getNumIdle(), getNumWaiters(), var2.getMessage()));
            throw new RuntimeException("获取Jedis实例异常");
        }
    }

    public static synchronized void returnResource(Jedis redis) {
        if (redis != null) {
            try {
                redis.close();
            } catch (Exception var2) {
                logger.warn("RedisUtil.returnResource - " + var2.getMessage());
            }
        }

    }

    public static String set(String key, String value) {
        Jedis j = null;

        try {
            validateValue(key, value);
            j = getJedis();
            String var3 = j.set(key, value);
            return var3;
        } catch (Exception var7) {
            logger.error("RedisUtil.set(" + key + "," + value + ") - " + var7.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String setExpire(String key, String value, int seconds) {
        Jedis j = null;

        try {
            validateValue(key, value);
            j = getJedis();
            String v = j.set(key, value);
            j.expire(key, seconds);
            String var5 = v;
            return var5;
        } catch (Exception var9) {
            logger.error("RedisUtil.setExpire(" + key + "," + value + ") - " + var9.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String setExpireAt(String key, String value, long l) {
        Jedis j = null;

        try {
            validateValue(key, value);
            j = getJedis();
            String v = j.set(key, value);
            j.pexpireAt(key, l);
            String var6 = v;
            return var6;
        } catch (Exception var10) {
            logger.error("RedisUtil.setPexpireAt(" + key + "," + value + "," + l + ") - " + var10.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String get(String key) {
        Jedis j = null;

        try {
            j = getJedis();
            String var2 = j.get(key);
            return var2;
        } catch (Exception var6) {
            logger.error("RedisUtil.get(" + key + ") - " + var6.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String mset(String... keyvalues) {
        Jedis j = null;

        try {
            validateValue(keyvalues);
            j = getJedis();
            String var2 = j.mset(keyvalues);
            return var2;
        } catch (Exception var6) {
            logger.error("RedisUtil.mset(" + keyvalues + ") - " + var6.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public List<String> mget(String... keys) {
        Jedis j = null;

        try {
            j = getJedis();
            List var3 = j.mget(keys);
            return var3;
        } catch (Exception var7) {
            logger.error("RedisUtil.mget(" + keys + ") - " + var7.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static Long del(String... keys) throws IOException {
        Jedis j = null;

        try {
            validateValue(keys);
            j = getJedis();
            Long v = j.del(keys);

            for(int i = 0; i < keys.length; ++i) {
                j.del(serialize(keys[i]));
            }

            Long var9 = v;
            return var9;
        } catch (Exception var7) {
            logger.error("RedisUtil.del(" + keys + ") - " + var7.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static Set<String> keys() {
        Jedis j = null;

        try {
            j = getJedis();
            Set var1 = j.keys("*");
            return var1;
        } catch (Exception var5) {
            logger.error("RedisUtil.keys() - " + var5.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static Set<String> keys(String key) {
        Jedis j = null;

        try {
            j = getJedis();
            Set var2 = j.keys("*" + key + "*");
            return var2;
        } catch (Exception var6) {
            logger.error("RedisUtil.keys(" + key + ") - " + var6.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String hget(String key, String field) {
        Jedis j = null;

        try {
            j = getJedis();
            String var3 = j.hget(key, field);
            return var3;
        } catch (Exception var7) {
            logger.error("RedisUtil.hget(" + key + "," + field + ") - " + var7.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static Long hset(String key, String field, String value) {
        Jedis j = null;

        try {
            validateValue(key, field, value);
            j = getJedis();
            Long var4 = j.hset(key, field, value);
            return var4;
        } catch (Exception var8) {
            logger.error("RedisUtil.hset(" + key + "," + field + "," + value + ") - " + var8.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static byte[] hget(byte[] key, byte[] field) {
        Jedis j = null;

        try {
            j = getJedis();
            byte[] var3 = j.hget(key, field);
            return var3;
        } catch (Exception var7) {
            logger.error("RedisUtil.hget(" + key + "," + field + ") - " + var7.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static Long hset(byte[] key, byte[] field, byte[] value) {
        Jedis j = null;

        try {
            validateValue(key, field, value);
            j = getJedis();
            Long var4 = j.hset(key, field, value);
            return var4;
        } catch (Exception var8) {
            logger.error("RedisUtil.hset(" + key + "," + field + "," + value + ") - " + var8.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String hmset(String key, Map<String, String> hash) {
        Jedis j = null;

        try {
            validateValue(key, hash);
            j = getJedis();
            String var3 = j.hmset(key, hash);
            return var3;
        } catch (Exception var7) {
            logger.error("RedisUtil.hmset(" + key + "," + hash + ") - " + var7.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String hmsetPexpireAt(String key, Map<String, String> hash, long l) {
        Jedis j = null;

        try {
            validateValue(key, hash);
            j = getJedis();
            String v = j.hmset(key, hash);
            j.pexpireAt(key, l);
            String var6 = v;
            return var6;
        } catch (Exception var10) {
            logger.error("RedisUtil.hmsetPexpireAt(" + key + "," + hash + "," + l + ") - " + var10.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String hmsetExpire(String key, Map<String, String> hash, int i) {
        Jedis j = null;

        try {
            validateValue(key, hash);
            j = getJedis();
            String v = j.hmset(key, hash);
            j.expire(key, i);
            String var5 = v;
            return var5;
        } catch (Exception var9) {
            logger.error("RedisUtil.hmsetExpire(" + key + "," + hash + "," + i + ") - " + var9.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static List<String> hmget(String key, String... fields) {
        Jedis j = null;

        try {
            validateValue(key, fields);
            j = getJedis();
            List var3 = j.hmget(key, fields);
            return var3;
        } catch (Exception var7) {
            logger.error("RedisUtil.hmget(" + key + "," + fields + ") - " + var7.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static Map<String, String> hgetAll(String key) {
        Jedis j = null;

        try {
            j = getJedis();
            Map var2 = j.hgetAll(key);
            return var2;
        } catch (Exception var6) {
            logger.error("RedisUtil.hgetAll(" + key + ") - " + var6.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static Long hmdel(String key, String... fields) {
        Jedis j = null;

        try {
            validateValue(key, fields);
            j = getJedis();
            Long var3 = j.hdel(key, fields);
            return var3;
        } catch (Exception var7) {
            logger.error("RedisUtil.hmdel(" + key + "," + fields + ") - " + var7.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static Long hmdel(byte[] key, byte[]... field) {
        Jedis j = null;

        try {
            validateValue(key, field);
            j = getJedis();
            Long var3 = j.hdel(key, field);
            return var3;
        } catch (Exception var7) {
            logger.error("RedisUtil.hmdel(" + key + "," + field + ") - " + var7.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static Long rpush(String key, String... strings) {
        Jedis j = null;

        try {
            validateValue(key, strings);
            j = getJedis();
            Long var3 = j.rpush(key, strings);
            return var3;
        } catch (Exception var7) {
            logger.error("RedisUtil.rpush(" + key + "," + strings + ") - " + var7.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static Long lpush(String key, String... strings) {
        Jedis j = null;

        try {
            validateValue(key, strings);
            j = getJedis();
            Long var3 = j.lpush(key, strings);
            return var3;
        } catch (Exception var7) {
            logger.error("RedisUtil.rpush(" + key + "," + strings + ") - " + var7.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String rpop(String key) {
        Jedis j = null;

        try {
            validateValue(key);
            j = getJedis();
            String var2 = j.rpop(key);
            return var2;
        } catch (Exception var6) {
            logger.error("RedisUtil.rpop(" + key + ") - " + var6.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String lpop(String key) {
        Jedis j = null;

        try {
            validateValue(key);
            j = getJedis();
            String var2 = j.lpop(key);
            return var2;
        } catch (Exception var6) {
            logger.error("RedisUtil.lpop(" + key + ") - " + var6.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static Long llen(String key) {
        Jedis j = null;

        try {
            validateValue(key);
            j = getJedis();
            Long var2 = j.llen(key);
            return var2;
        } catch (Exception var6) {
            logger.error("RedisUtil.llen(" + key + ") - " + var6.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static List<String> lrange(String key, long start, long end) {
        Jedis j = null;

        try {
            validateValue(key);
            j = getJedis();
            List var6 = j.lrange(key, start, end);
            return var6;
        } catch (Exception var10) {
            logger.error("RedisUtil.lrange(" + key + "," + start + "," + end + ") - " + var10.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static Long lrem(String key, long count, String value) {
        Jedis j = null;

        try {
            validateValue(key, value);
            j = getJedis();
            Long var5 = j.lrem(key, count, value);
            return var5;
        } catch (Exception var9) {
            logger.error("RedisUtil.lrem(" + key + "," + count + "," + value + ") - " + var9.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String lset(String key, long index, String value) {
        Jedis j = null;

        try {
            validateValue(key, value);
            j = getJedis();
            String var5 = j.lset(key, index, value);
            return var5;
        } catch (Exception var9) {
            logger.error("RedisUtil.lset(" + key + "," + index + "," + value + ") - " + var9.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String ltrim(String key, long start, long end) {
        Jedis j = null;

        try {
            validateValue(key);
            j = getJedis();
            j.ltrim(key, start, end);
            String var7 = j.ltrim(key, start, end);
            return var7;
        } catch (Exception var11) {
            logger.error("RedisUtil.ltrim(" + key + "," + start + "," + end + ") - " + var11.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String lindex(String key, long index) {
        Jedis j = null;

        try {
            validateValue(key);
            j = getJedis();
            String var4 = j.lindex(key, index);
            return var4;
        } catch (Exception var8) {
            logger.error("RedisUtil.lindex(" + key + "," + index + ") - " + var8.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String rpoplpush(String srckey, String dstkey) {
        Jedis j = null;

        try {
            validateValue(srckey, dstkey);
            j = getJedis();
            String var3 = j.rpoplpush(srckey, dstkey);
            return var3;
        } catch (Exception var7) {
            logger.error("RedisUtil.rpoplpush(" + srckey + "," + dstkey + ") - " + var7.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        } else {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                return baos.toByteArray();
            } catch (IOException var3) {
                return null;
            }
        }
    }

    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        } else {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            } catch (IOException var3) {
                return null;
            } catch (ClassNotFoundException var4) {
                return null;
            }
        }
    }

    public static String setObject(String key, Object o, int i) throws IOException {
        Jedis j = null;

        try {
            validateValue(key, o);
            j = getJedis();
            String v = j.set(serialize(key), serialize(o));
            if (i != -1) {
                j.expire(serialize(key), i);
            }

            logger.warn("redis保存对象");
            String var5 = v;
            return var5;
        } catch (Exception var9) {
            logger.error("RedisUtil.setObject(" + key + "," + o + "," + i + ") - " + var9.getMessage());
        } finally {
            returnResource(j);
        }

        return null;
    }

    public static String setObject(String key, Object o) throws IOException {
        DEFAULT_EXPIRE_TIME = StringUtils.isEmpty(CommonProperties.get("redis.default_expire_time")) ? 14400 : Integer.parseInt(CommonProperties.get("redis.default_expire_time").toString());
        return setObject(key, o, DEFAULT_EXPIRE_TIME);
    }

    public static Object getObject(String key) throws IOException, ClassNotFoundException {
        Jedis j = null;

        Object var3;
        try {
            validateValue(key);
            j = getJedis();
            byte[] v = j.get(serialize(key));
            if (v == null) {
                var3 = null;
                return var3;
            }

            var3 = unserialize(v);
        } catch (Exception var7) {
            logger.error(String.format("RedisUtil.getObject(%s) - %s", key, var7.getMessage()));
            return null;
        } finally {
            returnResource(j);
        }

        return var3;
    }

    public static void validateValue(Object... value) throws Exception {
        if (value == null) {
            throw new Exception("Redis无法处理值为null的数据");
        } else {
            Object[] var1 = value;
            int var2 = value.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Object obj = var1[var3];
                if (obj == null) {
                    throw new Exception("Redis无法处理值为null的数据");
                }

                if (obj instanceof Map) {
                    Iterator iterator = ((Map)obj).entrySet().iterator();

                    while(iterator.hasNext()) {
                        Map.Entry<String, Object> e = (Map.Entry)iterator.next();
                        if (e.getValue() == null) {
                            throw new Exception("Redis无法处理值为null的数据");
                        }
                    }
                }
            }

        }
    }

    public static int getNumActive() {
        return pool != null && !pool.isClosed() ? pool.getNumActive() : -1;
    }

    public static int getNumIdle() {
        return pool != null && !pool.isClosed() ? pool.getNumIdle() : -1;
    }

    public static int getNumWaiters() {
        return pool != null && !pool.isClosed() ? pool.getNumWaiters() : -1;
    }

    public static String getRedisHost() {
        return redisHost;
    }

    public static void setRedisHost(String redisHost) {
        redisHost = redisHost;
    }

    public static int getRedisPort() {
        return redisPort;
    }

    public static void setRedisPort(int redisPort) {
        redisPort = redisPort;
    }
}
