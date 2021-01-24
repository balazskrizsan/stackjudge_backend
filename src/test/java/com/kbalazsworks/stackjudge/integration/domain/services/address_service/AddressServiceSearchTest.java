package com.kbalazsworks.stackjudge.integration.domain.services.address_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.services.AddressService;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class AddressServiceSearchTest extends AbstractIntegrationTest
{
    @Autowired
    private AddressService addressService;

    private record TestData(List<Long> testedCompanyIds, Map<Long, List<Long>> expectedFoundList)
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
                    "classpath:test/sqls/preset_add_1_company.sql",
                    "classpath:test/sqls/preset_add_1_address.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void checkingTheResultFields_allShouldBeOk()
    {
        // Arrange
        List<Long> testedCompanyId = List.of(CompanyFakeBuilder.defaultId1);
        Map<Long, List<Address>> expectedAddresses = Map.of(
            CompanyFakeBuilder.defaultId1,
            new AddressFakeBuilder().buildAsList()
        );

        // Act
        Map<Long, List<Address>> actualAddresses = addressService.search(testedCompanyId);

        // Assert
        assertThat(actualAddresses).isEqualTo(expectedAddresses);
    }

    private TestData provider(int repetition)
    {
        if (1 == repetition)
        {
            return new TestData(List.of(1L), new HashMap<>());
        }

        if (2 == repetition)
        {
            return new TestData(
                List.of(CompanyFakeBuilder.defaultId2),
                Map.of(
                    CompanyFakeBuilder.defaultId2,
                    List.of(AddressFakeBuilder.defaultId2, AddressFakeBuilder.defaultId3)
                )
            );
        }

        if (3 == repetition)
        {
            return new TestData(
                List.of(CompanyFakeBuilder.defaultId1, CompanyFakeBuilder.defaultId2),
                Map.of(
                    CompanyFakeBuilder.defaultId1, List.of(AddressFakeBuilder.defaultId1),
                    CompanyFakeBuilder.defaultId2, List.of(AddressFakeBuilder.defaultId2, AddressFakeBuilder.defaultId3)
                )
            );
        }

        throw new JUnitException("TestData not found with repetition#" + repetition);
    }

    @RepeatedTest(value = 3)
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_10_companies.sql",
                    "classpath:test/sqls/preset_add_10_address.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void checkingTheResultIds_validatedByProvider(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData testData = provider(repetitionInfo.getCurrentRepetition());

        // Act
        Map<Long, List<Long>> actualIds = new HashMap<>();
        addressService.search(testData.testedCompanyIds).forEach(
            (companyId, addresses) ->
                actualIds.put(companyId, addresses.stream().map(Address::id).collect(Collectors.toList()))
        );

        // Assert
        assertThat(actualIds).isEqualTo(testData.expectedFoundList);
    }
}
