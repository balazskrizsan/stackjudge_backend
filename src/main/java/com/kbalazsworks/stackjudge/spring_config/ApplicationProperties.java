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

    @Value("${health_check.env_var_test}")
    private String healthCheckEnvVarTest;

    public String getHealthCheckEnvVarTest()
    {
        return healthCheckEnvVarTest;
    }

    @Value("${searchly.url}")
    private String searchlyUrl;

    public String getSearchlyUrl()
    {
        return searchlyUrl;
    }

    @Value("${server.env}")
    private String serverEnv;

    public String getServerEnv()
    {
        return serverEnv;
    }

    //    @Value("${is_search_box_log_enabled}")
    private String isSearchBoxLogEnabled = "false";

    public boolean isSearchBoxLogEnabled()
    {
        return isSearchBoxLogEnabled.equals("true");
    }

    @Value("${aws.s3.cdn_bucket}")
    private String awsS3CdnBucket;

    public String getAwsS3CdnBucket()
    {
        return awsS3CdnBucket;
    }

    @Value("${aws.access_key}")
    private String awsAccessKey;

    public String getAwsAccessKey()
    {
        return awsAccessKey;
    }

    @Value("${aws.secret_key}")
    private String awsSecretKey;

    public String getAwsSecretKey()
    {
        return awsSecretKey;
    }

    @Value("${facebook.client.id}")
    private String facebookClientId;

    public String getFacebookClientId()
    {
        return facebookClientId;
    }

    @Value("${facebook.client.secret}")
    private String facebookClientSecret;

    public String getFacebookClientSecret()
    {
        return facebookClientSecret;
    }

    @Value("${facebook.callback.url}")
    private String facebookCallbackUrl;

    public String getFacebookCallbackUrl()
    {
        return facebookCallbackUrl;
    }

    @Value("${auth.jwt.secret}")
    private String jwtSecret;

    public String getJwtSecret()
    {
        return jwtSecret;
    }
}
