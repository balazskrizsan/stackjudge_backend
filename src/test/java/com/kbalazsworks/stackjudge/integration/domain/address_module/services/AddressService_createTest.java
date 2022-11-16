package com.kbalazsworks.stackjudge.integration.domain.address_module.services;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.db.tables.records.AddressRecord;
import com.kbalazsworks.stackjudge.domain.address_module.entities.Address;
import com.kbalazsworks.stackjudge.domain.company_module.exceptions.CompanyHttpException;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.integration.annotations.TruncateAllTables;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class AddressService_createTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    static class Provider_insertOneRecordToTheDbWithCompany_perfect implements ArgumentsProvider
    {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context)
        {
            return Stream.of(
                Arguments.of(new AddressFakeBuilder().build(), new AddressFakeBuilder().build()),
                Arguments.of(
                    new AddressFakeBuilder().manualMarkerLat(null).manualMarkerLng(null).build(),
                    new AddressFakeBuilder().manualMarkerLat(null).manualMarkerLng(null).build()
                )
            );
        }
    }

    @ParameterizedTest(name = "{index} => testedAddress={0}")
    @ArgumentsSource(Provider_insertOneRecordToTheDbWithCompany_perfect.class)
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
    public void insertOneRecordToTheDbWithCompany_perfect(Address testedAddress, Address expectedAddress)
    {
        // Arrange
        long testedCompanyId = CompanyFakeBuilder.defaultId1;
        long testedAddressId = AddressFakeBuilder.defaultId1;

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
