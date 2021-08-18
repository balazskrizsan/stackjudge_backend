package com.kbalazsworks.stackjudge.integration.domain.services.company_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyGetServiceResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.fake_builders.*;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class CompanyServiceGetTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    private record TestData(
        Long testedCompanyId,
        List<Short> testedRequestRelationIds,
        CompanyGetServiceResponse expectedResponse
    )
    {
    }

    @Test
    public void VintageHack()
    {
        assertThat(true).isTrue();
    }

    // @todo: add user list test
    private TestData provider(int repetition)
    {
        long testedCompanyId    = CompanyFakeBuilder.defaultId1;
        Company expectedCompany = new CompanyFakeBuilder().build();
        long expectedGroupId    = GroupFakeBuilder.defaultId1;

        if (repetition == 1)
        {
            return new TestData(
                testedCompanyId,
                null,
                new CompanyGetServiceResponse(
                    expectedCompany,
                    null,
                    null,
                    null,
                    null,
                    null,
                    new HashMap<>()
                )
            );
        }
        if (repetition == 2)
        {
            return new TestData(
                testedCompanyId,
                List.of((short) 1, (short) 2, (short) 3, (short) 5),
                new CompanyGetServiceResponse(
                    expectedCompany,
                    new CompanyStatistic(expectedCompany.id(), 0, 1, 0, 0),
                    new RecursiveGroupTreeFakeBuilder().buildAsList(),
                    new AddressFakeBuilder().buildAsList(),
                    Map.of(
                        AddressFakeBuilder.defaultId1,
                        Map.of(MapPositionEnum.COMPANY_HEADER, new StaticMapResponseFakeBuilder().build(),
                            MapPositionEnum.COMPANY_LEFT, new StaticMapResponseFakeBuilder().build1_2()
                        )
                    ),
                    Map.of(expectedGroupId, new ReviewFakeBuilder().buildAsList()),
                    new HashMap<>()
                )
            );
        }

        throw getRepeatException(repetition);
    }

    @RepeatedTest(2)
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_1_company.sql",
                    "classpath:test/sqls/preset_add_1_address.sql",
                    "classpath:test/sqls/preset_add_1_group.sql",
                    "classpath:test/sqls/preset_add_1_review.sql",
                    "classpath:test/sqls/preset_add_1plus1_googole_static_map_cache.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void findTheInsertedCompanyAndRelatedInfo_byProvider(RepetitionInfo repetitionInfo)
    {
        // Arrange - In preset
        TestData tD = provider(repetitionInfo.getCurrentRepetition());

        // Act
        CompanyGetServiceResponse actualResponse = serviceFactory.getCompanyService().get(
            tD.testedCompanyId,
            tD.testedRequestRelationIds
        );

        // Assert
        assertThat(actualResponse).isEqualTo(tD.expectedResponse);
    }
}
