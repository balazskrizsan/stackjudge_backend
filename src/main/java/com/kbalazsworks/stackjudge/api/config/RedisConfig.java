package com.kbalazsworks.stackjudge.api.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;

    //5 minutes
    private final int TIMEOUT = 300000;

    //1 minute
    private final int EVICTION_RUN_FREQUENCY = 60000;

    //Optimization: https://www.alibabacloud.com/help/doc-detail/98726.htm#section-m2c-5kr-zfb
    //https://gist.github.com/JonCole/925630df72be1351b21440625ff2671f
    @Bean
    public JedisPool jedisPool() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxWaitMillis(TIMEOUT);
        config.setTestWhileIdle(true);
        config.setTimeBetweenEvictionRunsMillis(EVICTION_RUN_FREQUENCY);

        return new JedisPool(config, host, port, TIMEOUT, password);
    }

}
