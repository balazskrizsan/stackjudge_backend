package com.kbalazsworks.stackjudge.common.configuration;

import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.util.IsolationLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class SqlConfiguration
{
    private final ApplicationProperties ap;

    @Bean
    @Primary
    public HikariDataSource dataSource()
    {
        HikariConfig config = new HikariConfig();

        config.setTransactionIsolation(IsolationLevel.TRANSACTION_READ_COMMITTED.name());
        config.setJdbcUrl(ap.getDataSourceUrl());
        config.setUsername(ap.getDataSourceUsername());
        config.setPassword(ap.getDataSourcePassword());
        config.setMaximumPoolSize(ap.getDataSourceHikariMaximumPoolSize());
        config.setMinimumIdle(ap.getDataSourceHikariMinimumIdle());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "1000");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(config);
    }
}
