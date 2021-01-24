package com.kbalazsworks.stackjudge.integration.domain.services.company_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.entities.Group;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyGetServiceResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.fake_builders.*;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
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
        CompanyGetServiceResponse expectedResponse
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
        Company testedCompany   = new CompanyFakeBuilder().build();
        Company expectedCompany = new CompanyFakeBuilder().build();
        Group   expectedGroup   = new GroupFakeBuilder().build();

        if (repetition == 1)
        {
            return new TestData(
                testedCompany.id(),
                null,
                new CompanyGetServiceResponse(
                    testedCompany,
                    null,
                    null,
                    null,
                    null
                )
            );
        }
        if (repetition == 2)
        {
            return new TestData(
                testedCompany.id(),
                List.of((short) 1, (short) 2, (short) 3, (short) 5),
                new CompanyGetServiceResponse(
                    expectedCompany,
                    new CompanyStatistic(expectedCompany.id(), 1, 0, 0, 0),
                    new RecursiveGroupTreeFakeBuilder().buildAsList(),
                    new AddressFakeBuilder().buildAsList(),
                    Map.of(expectedGroup.id(), new ReviewFakeBuilder().buildAsList())
                )
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
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_1_company.sql",
                    "classpath:test/sqls/preset_add_1_address.sql",
                    "classpath:test/sqls/preset_add_1_stack_group.sql",
                    "classpath:test/sqls/preset_add_1_review.sql",
                }
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
        CompanyGetServiceResponse actualResponse = companyService.get(
            testData.testedCompanyId,
            testData.testedRequestRelationIds
        );

        // Assert
        assertThat(actualResponse).isEqualTo(testData.expectedResponse);
    }
}
