package com.kbalazsworks.stackjudge.integration.domain.company_module.services;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.company_module.entities.Company;
import com.kbalazsworks.stackjudge.domain.company_module.services.CompanyService;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class CompanyService_deleteTest extends AbstractIntegrationTest
{
    @Autowired
    private CompanyService companyService;

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql", "classpath:test/sqls/preset_add_1_company.sql"}
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void deletingExistingRecord_perfect()
    {
        // Arrange - In preset
        long testedCompanyId = CompanyFakeBuilder.defaultId1;

        // Act
        companyService.delete(testedCompanyId);

        // Assert
        Company company = getQueryBuilder()
            .selectFrom(companyTable)
            .where(companyTable.ID.eq(testedCompanyId))
            .fetchOneInto(Company.class);

        assertThat(company).isNull();
    }
}
