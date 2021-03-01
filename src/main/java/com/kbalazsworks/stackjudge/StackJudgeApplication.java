package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
//@EnableSwagger2
//@EnableOpenApi
public class StackJudgeApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(StackJudgeApplication.class, args);
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

    @Bean(name = "transactionManager")
    @Primary
    DataSourceTransactionManager getDataSourceTransactionManager()
    {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "transactionAwareDataSource")
    TransactionAwareDataSourceProxy getTransactionAwareDataSourceProxy()
    {
        return new TransactionAwareDataSourceProxy(dataSource());
    }
}
