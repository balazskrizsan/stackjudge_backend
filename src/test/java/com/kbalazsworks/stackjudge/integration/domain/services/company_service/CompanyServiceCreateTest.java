package com.kbalazsworks.stackjudge.integration.domain.services.company_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.db.tables.records.AddressRecord;
import com.kbalazsworks.stackjudge.db.tables.records.CompanyRecord;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import com.kbalazsworks.stackjudge.integration.domain.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.integration.domain.fake_builders.CompanyFakeBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class CompanyServiceCreateTest extends AbstractIntegrationTest
{
    @Autowired
    private CompanyService companyService;

    @Test
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
    public void insertOneCompanyWithOneAddress_perfect()
    {
        // Arrange
        Company testedCompany     = new CompanyFakeBuilder().build();
        Address testedAddress     = new AddressFakeBuilder().build();
        long    expectedCompanyId = 23455487L;
        Company expectedCompany   = new CompanyFakeBuilder().setId(expectedCompanyId).build();
        long    expectedAddressId = 24562647L;
        Address expectedAddress = new AddressFakeBuilder()
            .setId(expectedAddressId)
            .setCompanyId(expectedCompanyId)
            .build();

        // Act
        companyService.create(testedCompany, testedAddress);

        // Assert
        CompanyRecord actualCompany = getQueryBuilder().selectFrom(companyTable).fetchOne();
        actualCompany.setId(expectedCompanyId);

        AddressRecord actualAddress = getQueryBuilder().selectFrom(addressTable).fetchOne();
        actualAddress.setId(expectedAddressId);
        actualAddress.setCompanyId(expectedCompanyId);

        Assert.assertEquals(actualCompany.into(Company.class), expectedCompany);
        Assert.assertEquals(actualAddress.into(Address.class), expectedAddress);
    }
}
