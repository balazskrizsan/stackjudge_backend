package com.kbalazsworks.stackjudge.spring_config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class ApplicationProperties
{
    @Value("${site.domain}")
    private String siteDomain;

    @Value("${site.frontend.host}")
    private String siteFrontendHost;

    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${health_check.env_var_test}")
    private String healthCheckEnvVarTest;

    @Value("${server.env}")
    private String serverEnv;

    @Value("${sj.ids.full_host}")
    private String sjIdsFullHost;

    @Value("${google.maps.key}")
    private String googleMapsKey;

    @Value("${redis.aspect.cache.enabled}")
    private String redisAspectCacheEnabled;

    public boolean getRedisAspectCacheEnabled()
    {
        return redisAspectCacheEnabled.trim().equals("true");
    }

    @Value("${sj.aws.full_host}")
    private String StuckJudgeAwsSdkHost;

    public String getStuckJudgeNotificationSdkHost()
    {
        return "https://localhost:84";
    }

    public String getStackJudgeSdkCertP12KeystoreFilePath()
    {
        return "classpath:keystore/v2/sjdev.p12";
    }

    public String getStackJudgeSdkCertP12KeystoreFilePassword()
    {
        return "password";
    }
}
