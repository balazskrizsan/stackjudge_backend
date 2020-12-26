package com.kbalazsworks.stackjudge.integration.domain.services.company_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.enums.paginator.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import com.kbalazsworks.stackjudge.integration.fake_builders.CompanyFakeBuilder;
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

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(true).isTrue();
    }

    private record TestData(
        List<Company> expectedList,
        long testedSeekId,
        int testedLimit,
        NavigationEnum testedNavigation)
    {
    }

    private TestData provider(int iteration)
    {
        List<Company> expectedList = new ArrayList<>()
        {
        };

        long           testedSeekId = 0;
        int            testedLimit  = 0;
        NavigationEnum navigation   = null;

        if (iteration == 1)
        {
            testedSeekId = 1;
            testedLimit  = 2;
            navigation   = NavigationEnum.FIRST;

            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(164985367L)
                    .setName("a company 1")
                    .setCompanySizeId((short) 1)
                    .setItSizeId((short) 1)
                    .setCreatedAt(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                    .setCreatedBy(1L)
                    .build()
            );
            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(245678965L)
                    .setName("a company 2")
                    .setCompanySizeId((short) 2)
                    .setItSizeId((short) 2)
                    .setCreatedAt(LocalDateTime.of(2022, 1, 1, 0, 0, 0))
                    .setCreatedBy(2L)
                    .build()
            );
        }

        if (iteration == 2)
        {
            testedSeekId = 1;
            testedLimit  = 2;
            navigation   = NavigationEnum.SECOND;

            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(346542314L)
                    .setName("a company 3")
                    .setCompanySizeId((short) 3)
                    .setItSizeId((short) 3)
                    .setCreatedAt(LocalDateTime.of(2023, 1, 1, 0, 0, 0))
                    .setCreatedBy(3L)
                    .build()
            );
            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(423165498L)
                    .setName("a company 4")
                    .setCompanySizeId((short) 4)
                    .setItSizeId((short) 4)
                    .setCreatedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                    .setCreatedBy(4L)
                    .build()
            );
        }

        if (iteration == 3)
        {
            testedSeekId = 1;
            testedLimit  = 2;
            navigation   = NavigationEnum.LAST_MINUS_1;

            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(733200321L)
                    .setName("a company 7")
                    .setCompanySizeId((short) 7)
                    .setItSizeId((short) 7)
                    .setCreatedAt(LocalDateTime.of(2027, 1, 1, 0, 0, 0))
                    .setCreatedBy(7L)
                    .build()
            );
            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(821356546L)
                    .setName("a company 8")
                    .setCompanySizeId((short) 8)
                    .setItSizeId((short) 8)
                    .setCreatedAt(LocalDateTime.of(2028, 1, 1, 0, 0, 0))
                    .setCreatedBy(8L)
                    .build()
            );
        }

        if (iteration == 4)
        {
            testedSeekId = 1;
            testedLimit  = 2;
            navigation   = NavigationEnum.LAST;

            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(922316542L)
                    .setName("a company 9")
                    .setCompanySizeId((short) 9)
                    .setItSizeId((short) 9)
                    .setCreatedAt(LocalDateTime.of(2029, 1, 1, 0, 0, 0))
                    .setCreatedBy(9L)
                    .build()
            );
            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(992354656L)
                    .setName("a company 10")
                    .setCompanySizeId((short) 10)
                    .setItSizeId((short) 10)
                    .setCreatedAt(LocalDateTime.of(2030, 1, 1, 0, 0, 0))
                    .setCreatedBy(10L)
                    .build()
            );
        }

        if (iteration == 5)
        {
            testedSeekId = 423165498;
            testedLimit  = 2;

            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(423165498L)
                    .setName("a company 4")
                    .setCompanySizeId((short) 4)
                    .setItSizeId((short) 4)
                    .setCreatedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                    .setCreatedBy(4L)
                    .build()
            );
            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(565432165L)
                    .setName("a company 5")
                    .setCompanySizeId((short) 5)
                    .setItSizeId((short) 5)
                    .setCreatedAt(LocalDateTime.of(2025, 1, 1, 0, 0, 0))
                    .setCreatedBy(5L)
                    .build()
            );
        }

        if (iteration == 6)
        {
            testedSeekId = 565432165;
            testedLimit  = 2;
            navigation   = NavigationEnum.CURRENT_PLUS_1;

            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(733200321L)
                    .setName("a company 7")
                    .setCompanySizeId((short) 7)
                    .setItSizeId((short) 7)
                    .setCreatedAt(LocalDateTime.of(2027, 1, 1, 0, 0, 0))
                    .setCreatedBy(7L)
                    .build()
            );
            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(821356546L)
                    .setName("a company 8")
                    .setCompanySizeId((short) 8)
                    .setItSizeId((short) 8)
                    .setCreatedAt(LocalDateTime.of(2028, 1, 1, 0, 0, 0))
                    .setCreatedBy(8L)
                    .build()
            );
        }

        if (iteration == 7)
        {
            testedSeekId = 565432165;
            testedLimit  = 2;
            navigation   = NavigationEnum.CURRENT_PLUS_2;

            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(922316542L)
                    .setName("a company 9")
                    .setCompanySizeId((short) 9)
                    .setItSizeId((short) 9)
                    .setCreatedAt(LocalDateTime.of(2029, 1, 1, 0, 0, 0))
                    .setCreatedBy(9L)
                    .build()
            );
            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(992354656L)
                    .setName("a company 10")
                    .setCompanySizeId((short) 10)
                    .setItSizeId((short) 10)
                    .setCreatedAt(LocalDateTime.of(2030, 1, 1, 0, 0, 0))
                    .setCreatedBy(10L)
                    .build()
            );
        }

        if (iteration == 8)
        {
            testedSeekId = 565432165;
            testedLimit  = 2;
            navigation   = NavigationEnum.CURRENT_MINUS_1;

            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(346542314L)
                    .setName("a company 3")
                    .setCompanySizeId((short) 3)
                    .setItSizeId((short) 3)
                    .setCreatedAt(LocalDateTime.of(2023, 1, 1, 0, 0, 0))
                    .setCreatedBy(3L)
                    .build()
            );
            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(423165498L)
                    .setName("a company 4")
                    .setCompanySizeId((short) 4)
                    .setItSizeId((short) 4)
                    .setCreatedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                    .setCreatedBy(4L)
                    .build()
            );
        }

        if (iteration == 9)
        {
            testedSeekId = 565432165;
            testedLimit  = 2;
            navigation   = NavigationEnum.CURRENT_MINUS_2;

            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(164985367L)
                    .setName("a company 1")
                    .setCompanySizeId((short) 1)
                    .setItSizeId((short) 1)
                    .setCreatedAt(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                    .setCreatedBy(1L)
                    .build()
            );
            expectedList.add(
                new CompanyFakeBuilder()
                    .setId(245678965L)
                    .setName("a company 2")
                    .setCompanySizeId((short) 2)
                    .setItSizeId((short) 2)
                    .setCreatedAt(LocalDateTime.of(2022, 1, 1, 0, 0, 0))
                    .setCreatedBy(2L)
                    .build()
            );
        }

        return new TestData(expectedList, testedSeekId, testedLimit, navigation);
    }

    @RepeatedTest(value = 9, name = RepeatedTest.LONG_DISPLAY_NAME)
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
    public void pagingTest_returnCheckByProvider(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData testData = provider(repetitionInfo.getCurrentRepetition());

        // Act
        NavigationEnum testedNavigation = testData.testedNavigation;
        List<Company> actualList = companyService.search(
            testData.testedSeekId,
            testData.testedLimit,
            testedNavigation
        );

        // Assert
        String assertMessage = "Error with navigation: ".concat(
            (testedNavigation == null) ? "NULL" : testedNavigation.toString()
        );
        assertThat(testData.expectedList).as(assertMessage).isEqualTo(actualList);
    }
}
