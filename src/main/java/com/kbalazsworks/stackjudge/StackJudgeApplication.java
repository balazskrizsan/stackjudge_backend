package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.api.controllers.account_controller.AccountConfig;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableCaching
public class StackJudgeApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(StackJudgeApplication.class, args);
    }

    @Bean
    public GroupedOpenApi frontendApi()
    {
        return GroupedOpenApi
            .builder()
            .group("frontend")
            .pathsToMatch(AccountConfig.openapiFrontendUrls.toArray(String[]::new))
            .build();
    }

    @Bean
    public ApplicationProperties applicationProperties()
    {
        return new ApplicationProperties();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public DataSource dataSource()
    {
        ApplicationProperties applicationProperties = applicationProperties();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(applicationProperties.getDataSourceDriverClassName());
        dataSource.setUrl(applicationProperties.getDataSourceUrl());
        dataSource.setUsername(applicationProperties.getDataSourceUsername());
        dataSource.setPassword(applicationProperties.getDataSourcePassword());

        return dataSource;
    }
}
