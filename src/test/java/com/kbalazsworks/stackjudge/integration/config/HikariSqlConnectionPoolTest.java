package com.kbalazsworks.stackjudge.integration.config;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.zaxxer.hikari.pool.HikariPool;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import nl.altindag.log.LogCaptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@Log4j2
public class HikariSqlConnectionPoolTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    private record Processor(Function<Integer, Boolean> fn, int i) implements Runnable
    {
        @Override
        public void run()
        {
            fn.apply(i);
        }
    }

    @SqlGroup(
      {
        @Sql(
          executionPhase = BEFORE_TEST_METHOD,
          config = @SqlConfig(transactionMode = ISOLATED),
          scripts = {
            "classpath:test/sqls/_truncate_tables.sql",
            "classpath:test/sqls/preset_add_1_company.sql"
          }
        ),
        @Sql(
          executionPhase = AFTER_TEST_METHOD,
          config = @SqlConfig(transactionMode = ISOLATED),
          scripts = {"classpath:test/sqls/_truncate_tables.sql"}
        )
      }
    )
    @Test
    @SneakyThrows
    public void hikariSqlConnectionPool_12insertsOn5threads_creates12recordsOn5connections()
    {
        // Arrange
        LogCaptor logCaptor = LogCaptor.forClass(HikariPool.class);
        int testedThreads = 5;
        int testedRuns = 12;
        int expectedLogNumber = 5;
        int expectedAddressRows = 12;
        String expectedLogMatch = "HikariPool-[\\d] - Added connection org.postgresql.jdbc.PgConnection@[a-z0-9]+";

        ExecutorService e = Executors.newFixedThreadPool(testedThreads);
        for (int i = 1; i <= testedRuns; i++)
        {
            e.submit(new Processor((x) -> {
                serviceFactory.getAddressService().create(new AddressFakeBuilder().build());

                return true;
            }, i));
        }

        // Act
        e.shutdown();
        e.awaitTermination(5, TimeUnit.SECONDS);

        // Assert
        long addressCount = getQueryBuilder().select().from(addressTable).execute();
        List<String> logs = logCaptor.getInfoLogs();

        assertAll(
          () -> assertThat(addressCount).isEqualTo(expectedAddressRows),
          () -> assertThat(logs.size()).isEqualTo(expectedLogNumber),
          () -> assertThat(logs.get(0)).matches(expectedLogMatch),
          () -> assertThat(logs.get(1)).matches(expectedLogMatch),
          () -> assertThat(logs.get(2)).matches(expectedLogMatch),
          () -> assertThat(logs.get(3)).matches(expectedLogMatch),
          () -> assertThat(logs.get(4)).matches(expectedLogMatch)
        );
    }
}
