package com.kbalazsworks.stackjudge.integration.domain.services.address_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.services.AddressService;
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
    public void checkingTheResultFields_allShouldBeOk()
    {
        // Arrange
        List<Long> testedCompanyId = List.of(164985367L);
        Map<Long, List<Address>> expectedAddresses = Map.of(
            164985367L,
            List.of(new Address(
                3452345L,
                164985367L,
                "Full address 1, 123, 1",
                11.11,
                22.22,
                33.33,
                44.44,
                LocalDateTime.of(2020, 11, 22, 11, 22, 33),
                333L
            ))
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
            return new TestData(List.of(733200321L), Map.of(733200321L, List.of(5452345L, 1321654L)));
        }

        if (3 == repetition)
        {
            return new TestData(
                List.of(164985367L, 733200321L),
                Map.of(
                    164985367L, List.of(3452345L),
                    733200321L, List.of(5452345L, 1321654L)
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
        Map<Long, List<Address>> actualResult = addressService.search(testData.testedCompanyIds);
        Map<Long, List<Long>>    actualIds    = new HashMap<>();
        actualResult.forEach(
            (companyId, addresses) ->
                actualIds.put(companyId, addresses.stream().map(Address::id).collect(Collectors.toList()))
        );

        // Assert
        assertThat(actualIds).isEqualTo(testData.expectedFoundList);
    }
}
