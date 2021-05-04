package com.kbalazsworks.stackjudge.spring_config;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationProperties
{
    @Value("${server.port}")
    private String serverPort;

    public String getServerPort()
    {
        return serverPort;
    }

    @Value("${logging.file}")
    private String loggingFile;

    public String getLoggingFile()
    {
        return loggingFile;
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

    @Value("${env_var_test}")
    private String getEnvVarTest;

    public String getEnvVarTest()
    {
        return getEnvVarTest;
    }

    @Value("${searchly.url}")
    private String searchlyUrl;

    public String getSearchlyUrl()
    {
        return searchlyUrl;
    }

    @Value("${env}")
    private String env;

    public String getEnv()
    {
        return env;
    }

    //    @Value("${is_search_box_log_enabled}")
    private String isSearchBoxLogEnabled = "false";

    public boolean isSearchBoxLogEnabled()
    {
        return isSearchBoxLogEnabled.equals("true");
    }

    @Value("${AWS_S3_CDN_BUCKET}")
    private String awsS3CdnBucket;

    public String getAwsS3CdnBucket()
    {
        return awsS3CdnBucket;
    }

    @Value("${AWS_ACCESS_KEY}")
    private String awsAccessKey;

    public String getAwsAccessKey()
    {
        return awsAccessKey;
    }

    @Value("${AWS_SECRET_KEY}")
    private String awsSecretKey;

    public String getAwsSecretKey()
    {
        return awsSecretKey;
    }

    // FACEBOOK

    @Value("${FACEBOOK_CLIENT_ID}")
    private String facebookClientId;

    public String getFacebookClientId()
    {
        return facebookClientId;
    }

    @Value("${FACEBOOK_CLIENT_SECRET}")
    private String facebookClientSecret;

    public String getFacebookClientSecret()
    {
        return facebookClientSecret;
    }

    @Value("${FACEBOOK_CALLBACK_URL}")
    private String facebookCallbackUrl;

    public String getFacebookCallbackUrl()
    {
        return facebookCallbackUrl;
    }
}
