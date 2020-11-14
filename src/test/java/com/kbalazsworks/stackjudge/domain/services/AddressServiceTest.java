package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.db.tables.records.AddressRecord;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.exceptions.AddressException;
import com.kbalazsworks.stackjudge.domain.mocks.AddressFakeBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class AddressServiceTest extends AbstractIntegrationTest
{
    @Autowired
    private AddressService addressService;

    @Test
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
    public void create_insertOneRecordToTheDbWithCompany_perfect()
    {
        // Arrange
        long    testedCompanyId = 164985367L;
        Address testedAddress   = new AddressFakeBuilder().setId(1111L).setCompanyId(testedCompanyId).build();

        // Act - Assert
        addressService.create(testedAddress);

        // Assert
        AddressRecord actualAddress = getQueryBuilder()
            .selectFrom(addressTable)
            .where(addressTable.COMPANY_ID.eq(testedCompanyId))
            .fetchOne();
        actualAddress.setId(1111L);

        assertEquals(actualAddress.into(Address.class), testedAddress);
    }

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
    public void create_insertOneRecordToTheDbWithoutCompany_throwsException()
    {
        // Arrange
        Address testedAddress = new AddressFakeBuilder().build();

        // Act - Assert
        Throwable exception = Assert.assertThrows(AddressException.class, () -> addressService.create(testedAddress));

        // Assert
        assertEquals("Missing company; id#222", exception.getMessage());
    }
}
