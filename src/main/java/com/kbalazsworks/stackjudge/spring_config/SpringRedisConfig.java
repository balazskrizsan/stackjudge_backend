package com.kbalazsworks.stackjudge.spring_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class SpringRedisConfig
{

    @Bean
    public static ConfigureRedisAction configureRedisAction()
    {
        return ConfigureRedisAction.NO_OP;
    }
}

