package com.kbalazsworks.stackjudge.integration.domain.services.company_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import com.kbalazsworks.stackjudge.integration.domain.fake_builders.CompanyFakeBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class CompanyServiceSearchTest extends AbstractIntegrationTest
{
    @Autowired
    private CompanyService companyService;

    @Test
    public void vintageHack()
    {
        Assert.assertTrue(true);
    }

    private record TestData(List<Company> expectedList, long testedSeekId, int testedLimit)
    {
    }

    private TestData provider(int iteration)
    {
        List<Company> expectedList = new ArrayList<>()
        {
        };
        long testedSeekId = 0;
        int  testedLimit  = 0;

        if (iteration == 1)
        {
            testedLimit = 10;

            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(164985367L)
                    .setName("a company 1")
                    .setCompanySizeId((short) 1)
                    .setItSizeId((short) 2)
                    .setCreatedAt(LocalDateTime.of(2021, 1, 2, 1, 2, 3))
                    .setCreatedBy(111L)
                    .build()
            );
            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(245678965L)
                    .setName("a company 2")
                    .setCompanySizeId((short) 3)
                    .setItSizeId((short) 4)
                    .setCreatedAt(LocalDateTime.of(2022, 3, 4, 4, 5, 6))
                    .setCreatedBy(222L)
                    .build()
            );
            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(854621354L)
                    .setName("a company 3")
                    .setCompanySizeId((short) 5)
                    .setItSizeId((short) 6)
                    .setCreatedAt(LocalDateTime.of(2023, 5, 6, 7, 8, 9))
                    .setCreatedBy(333L)
                    .build()
            );
        }

        if (iteration == 2)
        {
            testedSeekId = 245678964;
            testedLimit  = 10;

            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(245678965L)
                    .setName("a company 2")
                    .setCompanySizeId((short) 3)
                    .setItSizeId((short) 4)
                    .setCreatedAt(LocalDateTime.of(2022, 3, 4, 4, 5, 6))
                    .setCreatedBy(222L)
                    .build()
            );
            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(854621354L)
                    .setName("a company 3")
                    .setCompanySizeId((short) 5)
                    .setItSizeId((short) 6)
                    .setCreatedAt(LocalDateTime.of(2023, 5, 6, 7, 8, 9))
                    .setCreatedBy(333L)
                    .build()
            );
        }

        if (iteration == 3)
        {
            testedSeekId = 1;
            testedLimit  = 1;

            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(164985367L)
                    .setName("a company 1")
                    .setCompanySizeId((short) 1)
                    .setItSizeId((short) 2)
                    .setCreatedAt(LocalDateTime.of(2021, 1, 2, 1, 2, 3))
                    .setCreatedBy(111L)
                    .build()
            );
        }

        // iteration == 4 - with default vaules

        return new TestData(expectedList, testedSeekId, testedLimit);
    }

    @RepeatedTest(value = 4, name = RepeatedTest.LONG_DISPLAY_NAME)
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql", "classpath:test/sqls/preset_add_3_companies.sql"}
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void findAllRecords_perfect(RepetitionInfo repetitionInfo)
    {
        // Arrange - In preset
        TestData providerData = provider(repetitionInfo.getCurrentRepetition());

        // Act
        List<Company> companyList = companyService.search(providerData.testedSeekId(), providerData.testedLimit());

        // Assert
        Assert.assertEquals(providerData.expectedList(), companyList);
    }
}
