package com.kbalazsworks.stackjudge.integration.domain.services.address_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.db.tables.records.AddressRecord;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.exceptions.CompanyHttpException;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.integration.annotations.TruncateAllTables;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class AddressServiceCreateTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    private Address provider(int repetition) throws Exception
    {
        if (repetition == 1)
        {
            return new AddressFakeBuilder().build();
        }

        if (repetition == 2)
        {
            return new AddressFakeBuilder().manualMarkerLat(null).manualMarkerLng(null).build();
        }

        throw new Exception();
    }

    @RepeatedTest(value = 2, name = RepeatedTest.LONG_DISPLAY_NAME)
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
    public void insertOneRecordToTheDbWithCompany_perfect(RepetitionInfo repetitionInfo) throws Exception
    {
        // Arrange
        long    testedCompanyId = CompanyFakeBuilder.defaultId1;
        long    testedAddressId = AddressFakeBuilder.defaultId1;
        Address testedAddress   = provider(repetitionInfo.getCurrentRepetition());
        Address expectedAddress = provider(repetitionInfo.getCurrentRepetition());

        // Act
        serviceFactory.getAddressService().create(testedAddress);

        // Assert
        AddressRecord actualAddress = getQueryBuilder()
            .selectFrom(addressTable)
            .where(addressTable.COMPANY_ID.eq(testedCompanyId))
            .fetchOne();
        actualAddress.setId(testedAddressId);

        assertEquals(expectedAddress, actualAddress.into(Address.class));
    }

    @Test
    @TruncateAllTables
    public void insertOneRecordToTheDbWithoutCompany_throwsException()
    {
        // Arrange
        Address testedAddress = new AddressFakeBuilder().build();

        // Act - Assert
        assertThatThrownBy(() -> serviceFactory.getAddressService().create(testedAddress))
            .isInstanceOf((CompanyHttpException.class))
            .hasMessage("Company not found.");
    }
}
