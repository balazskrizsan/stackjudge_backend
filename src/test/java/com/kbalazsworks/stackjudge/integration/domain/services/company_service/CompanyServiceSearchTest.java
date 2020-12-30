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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class CompanyServiceSearchTest extends AbstractIntegrationTest
{
    @Autowired
    private CompanyService companyService;

    private record TestData(
        List<Long> expectedIdList,
        long testedSeekId,
        int testedLimit,
        NavigationEnum testedNavigation)
    {
    }

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_one_company.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void checkingAllFieldsFormDb_allAreOk()
    {
        // Arrange
        List<Company> expectedCompany = new ArrayList<>()
        {{
            add(
                new CompanyFakeBuilder().build()
            );
        }};

        // Act
        List<Company> actualList = companyService.search(0, 1, NavigationEnum.FIRST);

        // Assert
        assertThat(expectedCompany).isEqualTo(actualList);
    }

    private TestData provider(int iteration)
    {
        List<Long> expectedIdList = new ArrayList<>()
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

            expectedIdList.add(164985367L);
            expectedIdList.add(245678965L);
        }

        if (iteration == 2)
        {
            testedSeekId = 1;
            testedLimit  = 2;
            navigation   = NavigationEnum.SECOND;

            expectedIdList.add(346542314L);
            expectedIdList.add(423165498L);
        }

        if (iteration == 3)
        {
            testedSeekId = 1;
            testedLimit  = 2;
            navigation   = NavigationEnum.LAST_MINUS_1;

            expectedIdList.add(733200321L);
            expectedIdList.add(821356546L);
        }

        if (iteration == 4)
        {
            testedSeekId = 1;
            testedLimit  = 2;
            navigation   = NavigationEnum.LAST;

            expectedIdList.add(922316542L);
            expectedIdList.add(992354656L);
        }

        if (iteration == 5)
        {
            testedSeekId = 423165498;
            testedLimit  = 2;

            expectedIdList.add(423165498L);
            expectedIdList.add(565432165L);
        }

        if (iteration == 6)
        {
            testedSeekId = 565432165;
            testedLimit  = 2;
            navigation   = NavigationEnum.CURRENT_PLUS_1;

            expectedIdList.add(733200321L);
            expectedIdList.add(821356546L);
        }

        if (iteration == 7)
        {
            testedSeekId = 565432165;
            testedLimit  = 2;
            navigation   = NavigationEnum.CURRENT_PLUS_2;

            expectedIdList.add(922316542L);
            expectedIdList.add(992354656L);
        }

        if (iteration == 8)
        {
            testedSeekId = 565432165;
            testedLimit  = 2;
            navigation   = NavigationEnum.CURRENT_MINUS_1;

            expectedIdList.add(346542314L);
            expectedIdList.add(423165498L);
        }

        if (iteration == 9)
        {
            testedSeekId = 565432165;
            testedLimit  = 2;
            navigation   = NavigationEnum.CURRENT_MINUS_2;

            expectedIdList.add(164985367L);
            expectedIdList.add(245678965L);
        }

        return new TestData(expectedIdList, testedSeekId, testedLimit, navigation);
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
        List<Long> actualList = companyService.search(
            testData.testedSeekId,
            testData.testedLimit,
            testData.testedNavigation
        )
            .stream()
            .map(Company::id)
            .collect(Collectors.toList());

        // Assert
        String assertMessage = "Error with navigation: ".concat(
            (testData.testedNavigation == null) ? "NULL" : testData.testedNavigation.toString()
        );
        assertThat(testData.expectedIdList).as(assertMessage).isEqualTo(actualList);
    }
}
