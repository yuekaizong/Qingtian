package kaizone.songmaya.smartns.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.regex.Pattern;

@Configuration
public class RedisConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {

        JedisConnectionFactory jedisConnectionFactory = null;
        if (StringUtils.isNotBlank(redisProperties.getClusterNodes())) {
            String[] nodes = redisProperties.getClusterNodes().trim()
                    .split(Pattern.quote(","));
            RedisClusterConfiguration redisClusterConfiguration =
                    new RedisClusterConfiguration(Arrays.asList(nodes));
            jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration);
        } else {
            jedisConnectionFactory = new JedisConnectionFactory();
            jedisConnectionFactory.setHostName(redisProperties.getHost());
            jedisConnectionFactory.setPort(Integer.parseInt(redisProperties.getPort()));
        }

        JedisPoolConfig config = new JedisPoolConfig();
        //可用连接实例的最大数目，默认值为8；
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        if (redisProperties.getMaxTotal() != 0) {
            LOGGER.debug("JedisPoolConfig set maxTotal = " + String.valueOf(redisProperties.getMaxTotal()));
            config.setMaxTotal(redisProperties.getMaxTotal());
        }
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        if (redisProperties.getMaxIdle() != 0) {
            LOGGER.debug("JedisPoolConfig set maxIdle = " + String.valueOf(redisProperties.getMaxIdle()));
            config.setMaxIdle(redisProperties.getMaxIdle());
        }
        if (redisProperties.getMinIdle() != 0) {
            LOGGER.debug("JedisPoolConfig set minIdle = " + String.valueOf(redisProperties.getMinIdle()));
            config.setMinIdle(redisProperties.getMinIdle());
        }
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        if (redisProperties.getMaxWaitMillis() != 0) {
            LOGGER.debug("JedisPoolConfig set maxWaitMillis = " + String.valueOf(redisProperties.getMaxWaitMillis()));
            config.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
        }
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);

        if (redisProperties.getConnTimeout() != 0) {
            LOGGER.debug("JedisConnectionFactory set connTimeout = " + String.valueOf(redisProperties.getConnTimeout()));
            jedisConnectionFactory.setTimeout(redisProperties.getConnTimeout());
        }

        jedisConnectionFactory.setPoolConfig(config);
        jedisConnectionFactory.setUsePool(true);

        return jedisConnectionFactory;
    }

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setStringSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }


}
