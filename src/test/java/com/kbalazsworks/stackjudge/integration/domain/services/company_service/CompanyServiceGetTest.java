package com.kbalazsworks.stackjudge.integration.domain.services.company_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyGetServiceResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.integration.fake_builders.CompanyFakeBuilder;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class CompanyServiceGetTest extends AbstractIntegrationTest
{
    @Autowired
    private CompanyService companyService;

    private record TestData(
        Long testedCompanyId,
        List<Short> testedRequestRelationIds,
        Company expectedCompany,
        CompanyStatistic expectedCompanyStatistic
    )
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
            return new TestData(
                164985367L,
                null,
                new CompanyFakeBuilder().build(),
                null
            );
        }
        if (repetition == 2)
        {
            return new TestData(
                164985367L,
                new ArrayList<>()
                {{
                    add((short) 1);
                }},
                new CompanyFakeBuilder().build(),
                new CompanyStatistic(164985367L, 0, 0, 0, 0)
            );
        }

        throw new JUnitException("Missing test data on repetition#" + repetition);
    }

    @RepeatedTest(2)
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql", "classpath:test/sqls/preset_add_one_company.sql"}
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void findTheInsertedCompanyAndRelatedInfo_byProvider(RepetitionInfo repetitionInfo)
    {
        // Arrange - In preset
        TestData testData = provider(repetitionInfo.getCurrentRepetition());

        // Act
        CompanyGetServiceResponse companyGetServiceResponse = companyService.get(
            testData.testedCompanyId,
            testData.testedRequestRelationIds
        );

        // Assert
        assertAll(
            () -> assertThat(companyGetServiceResponse.company()).isEqualTo(testData.expectedCompany),
            () -> assertThat(companyGetServiceResponse.companyStatistic()).isEqualTo(testData.expectedCompanyStatistic)
        );
    }
}
