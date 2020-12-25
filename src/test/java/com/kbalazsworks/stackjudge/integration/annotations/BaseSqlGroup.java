package com.kbalazsworks.stackjudge.integration.annotations;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.lang.annotation.*;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SqlGroup(
    {
        @Sql(
            executionPhase = BEFORE_TEST_METHOD,
            config = @SqlConfig(transactionMode = ISOLATED),
            scripts = {"classpath:test/sqls/_truncate_tables.sql"}
        ),
        @Sql(
            executionPhase = AFTER_TEST_METHOD,
            config = @SqlConfig(transactionMode = ISOLATED),
            scripts = {"classpath:test/sqls/_truncate_tables.sql"}
        )
    }
)
public @interface BaseSqlGroup
{
}
