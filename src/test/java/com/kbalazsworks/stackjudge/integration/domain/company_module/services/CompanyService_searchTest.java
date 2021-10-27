package com.kbalazsworks.stackjudge.integration.domain.company_module.services;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.company_module.entities.Company;
import com.kbalazsworks.stackjudge.domain.review_module.enums.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.company_module.services.CompanyService;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class CompanyService_searchTest extends AbstractIntegrationTest
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
    public void checkingAllFieldsFormDb_allAreOk()
    {
        // Arrange
        List<Company> expectedCompany = new CompanyFakeBuilder().buildAsList();

        // Act
        List<Company> actualList = companyService.search(0, 1, NavigationEnum.FIRST);

        // Assert
        assertThat(expectedCompany).usingRecursiveComparison().isEqualTo(actualList);
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

            expectedIdList.add(CompanyFakeBuilder.defaultId1);
            expectedIdList.add(CompanyFakeBuilder.defaultId2);
        }

        if (iteration == 2)
        {
            testedSeekId = 1;
            testedLimit  = 2;
            navigation   = NavigationEnum.SECOND;

            expectedIdList.add(CompanyFakeBuilder.defaultId3);
            expectedIdList.add(CompanyFakeBuilder.defaultId4);
        }

        if (iteration == 3)
        {
            testedSeekId = 1;
            testedLimit  = 2;
            navigation   = NavigationEnum.LAST_MINUS_1;

            expectedIdList.add(CompanyFakeBuilder.defaultId7);
            expectedIdList.add(CompanyFakeBuilder.defaultId8);
        }

        if (iteration == 4)
        {
            testedSeekId = 1;
            testedLimit  = 2;
            navigation   = NavigationEnum.LAST;

            expectedIdList.add(CompanyFakeBuilder.defaultId9);
            expectedIdList.add(CompanyFakeBuilder.defaultId10);
        }

        if (iteration == 5)
        {
            testedSeekId = CompanyFakeBuilder.defaultId5;
            testedLimit  = 2;

            expectedIdList.add(CompanyFakeBuilder.defaultId5);
            expectedIdList.add(CompanyFakeBuilder.defaultId6);
        }

        if (iteration == 6)
        {
            testedSeekId = CompanyFakeBuilder.defaultId5;
            testedLimit  = 2;
            navigation   = NavigationEnum.CURRENT_PLUS_1;

            expectedIdList.add(CompanyFakeBuilder.defaultId7);
            expectedIdList.add(CompanyFakeBuilder.defaultId8);
        }

        if (iteration == 7)
        {
            testedSeekId = CompanyFakeBuilder.defaultId5;
            testedLimit  = 2;
            navigation   = NavigationEnum.CURRENT_PLUS_2;

            expectedIdList.add(CompanyFakeBuilder.defaultId9);
            expectedIdList.add(CompanyFakeBuilder.defaultId10);
        }

        if (iteration == 8)
        {
            testedSeekId = CompanyFakeBuilder.defaultId5;
            testedLimit  = 2;
            navigation   = NavigationEnum.CURRENT_MINUS_1;

            expectedIdList.add(CompanyFakeBuilder.defaultId3);
            expectedIdList.add(CompanyFakeBuilder.defaultId4);
        }

        if (iteration == 9)
        {
            testedSeekId = CompanyFakeBuilder.defaultId5;
            testedLimit  = 2;
            navigation   = NavigationEnum.CURRENT_MINUS_2;

            expectedIdList.add(CompanyFakeBuilder.defaultId1);
            expectedIdList.add(CompanyFakeBuilder.defaultId2);
        }

        if (iteration == 10)
        {
            testedSeekId = CompanyFakeBuilder.defaultId5;
            testedLimit  = 1;
            navigation   = NavigationEnum.EXACTLY_ONE_RECORD;

            expectedIdList.add(CompanyFakeBuilder.defaultId5);
        }

        return new TestData(expectedIdList, testedSeekId, testedLimit, navigation);
    }

    @RepeatedTest(value = 10)
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
            .map(Company::getId)
            .collect(Collectors.toList());

        // Assert
        String assertMessage = "Error with navigation: ".concat(
            (testData.testedNavigation == null) ? "NULL" : testData.testedNavigation.toString()
        );
        assertThat(testData.expectedIdList).as(assertMessage).isEqualTo(actualList);
    }
}
