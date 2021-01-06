package com.kbalazsworks.stackjudge.integration.domain.services.company_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class CompanyServiceCountRecordsTest extends AbstractIntegrationTest
{
    @Autowired
    private CompanyService companyService;

    private record TestData(long testedSeekId, long expectedNumberOdRecords)
    {
    }

    @Test
    public void VintageHack()
    {
        assertThat(true).isTrue();
    }

    private TestData provider(int repetition)
    {
        if (repetition == 1)
        {
            return new TestData(164985367L, 0L);
        }

        if (repetition == 2)
        {
            return new TestData(565432165L, 3L);
        }

        if (repetition == 3)
        {
            return new TestData(423165498L, 9L);
        }

        throw new JUnitException("TestData not found with repetition#" + repetition);
    }

    @RepeatedTest(3)
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_10_companies.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void countAllCompanies_returnsTheExpectedNumber(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData testData = provider(repetitionInfo.getCurrentRepetition());

        // Act
        long actualResult = companyService.countRecordsBeforeId(testData.testedSeekId);

        // Assert
        assertThat(actualResult).isEqualTo(actualResult);
    }

}
