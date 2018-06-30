package com.orjrs.concurrency.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * ${todo}
 *
 * @author orjrs
 * @date 2018-06-1713:28
 */
@Configuration
public class RedisConfig {
    @Bean("redisPool")
    public JedisPool jedisPool(@Value("${redis.host}") String host,
                               @Value("${redis.port}") int port) {
        return new JedisPool(host, port);
    }
}
