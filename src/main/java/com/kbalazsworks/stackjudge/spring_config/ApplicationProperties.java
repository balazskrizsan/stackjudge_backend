package com.kbalazsworks.stackjudge.spring_config;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationProperties
{
    @Value("${site.domain}")
    private String siteDomain;

    public String getSiteDomain()
    {
        return siteDomain;
    }

    @Value("${site.frontend.host}")
    private String siteFrontendHost;

    public String getSiteFrontendHost()
    {
        return siteFrontendHost;
    }

    @Value("${server.port}")
    private String serverPort;

    public String getServerPort()
    {
        return serverPort;
    }

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    public String getDataSourceDriverClassName()
    {
        return driverClassName;
    }

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    public String getDataSourceUrl()
    {
        return dataSourceUrl;
    }

    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    public String getDataSourceUsername()
    {
        return dataSourceUsername;
    }

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    public String getDataSourcePassword()
    {
        return dataSourcePassword;
    }

    @Value("${spring.redis.host}")
    private String redisHost;

    public String getRedisHost()
    {
        return redisHost;
    }

    @Value("${spring.redis.password}")
    private String redisPassword;

    public String getRedisPassword()
    {
        return redisPassword;
    }

    @Value("${spring.redis.port}")
    private int redisPort;

    public int getRedisPort()
    {
        return redisPort;
    }

    @Value("${health_check.env_var_test}")
    private String healthCheckEnvVarTest;

    public String getHealthCheckEnvVarTest()
    {
        return healthCheckEnvVarTest;
    }

    @Value("${server.env}")
    private String serverEnv;

    public String getServerEnv()
    {
        return serverEnv;
    }

    @Value("${sj.ids.full_host}")
    private String sjIdsFullHost;

    public String getSjIdsFullHost()
    {
        return sjIdsFullHost;
    }

    @Value("${google.maps.key}")
    private String googleMapsKey;

    public String getGoogleMapsKey()
    {
        return googleMapsKey;
    }

    @Value("${redis.aspect.cache.enabled}")
    private String redisAspectCacheEnabled;

    public boolean getRedisAspectCacheEnabled()
    {
        return redisAspectCacheEnabled.trim().equals("true");
    }

    public String getStuckJudgeAwsSdkHost()
    {
        return "https://localhost:83";
    }

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
