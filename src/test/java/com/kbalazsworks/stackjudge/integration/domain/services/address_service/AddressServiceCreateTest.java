package com.kbalazsworks.stackjudge.integration.domain.services.address_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.db.tables.records.AddressRecord;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.exceptions.AddressException;
import com.kbalazsworks.stackjudge.domain.services.AddressService;
import com.kbalazsworks.stackjudge.integration.annotations.BaseSqlGroup;
import com.kbalazsworks.stackjudge.integration.domain.fake_builders.AddressFakeBuilder;
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
    private AddressService addressService;

    private Address provider(int repetition, long companyId, long testedAddressId) throws Exception
    {
        if (repetition == 1)
        {
            return new AddressFakeBuilder().setId(testedAddressId).setCompanyId(companyId).build();
        }

        if (repetition == 2)
        {
            return new AddressFakeBuilder()
                .setId(testedAddressId)
                .setCompanyId(companyId)
                .setManualMarkerLat(null)
                .setManualMarkerLng(null)
                .build();
        }

        throw new Exception();
    }

    @RepeatedTest(value = 2, name = RepeatedTest.LONG_DISPLAY_NAME)
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
    public void insertOneRecordToTheDbWithCompany_perfect(RepetitionInfo repetitionInfo) throws Exception
    {
        // Arrange
        long    testedCompanyId = 164985367L;
        long    testedAddressId = 1111L;
        Address testedAddress   = provider(repetitionInfo.getCurrentRepetition(), testedCompanyId, testedAddressId);
        Address expectedAddress = provider(repetitionInfo.getCurrentRepetition(), testedCompanyId, testedAddressId);

        // Act
        addressService.create(testedAddress);

        // Assert
        AddressRecord actualAddress = getQueryBuilder()
            .selectFrom(addressTable)
            .where(addressTable.COMPANY_ID.eq(testedCompanyId))
            .fetchOne();
        actualAddress.setId(testedAddressId);

        assertEquals(expectedAddress, actualAddress.into(Address.class));
    }

    @Test
    @BaseSqlGroup
    public void insertOneRecordToTheDbWithoutCompany_throwsException()
    {
        // Arrange
        Address testedAddress = new AddressFakeBuilder().build();

        // Act - Assert
        assertThatThrownBy(() -> addressService.create(testedAddress))
            .isInstanceOf((AddressException.class))
            .hasMessage("Missing company; id#222");
    }
}
