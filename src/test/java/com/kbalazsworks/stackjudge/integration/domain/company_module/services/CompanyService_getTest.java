package com.kbalazsworks.stackjudge.integration.domain.company_module.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.address_module.entities.CompanyAddresses;
import com.kbalazsworks.stackjudge.domain.company_module.entities.Company;
import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwners;
import com.kbalazsworks.stackjudge.domain.company_module.services.CompanyService;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.CompanyGetServiceResponse;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapPositionEnum;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GroupFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.IdsUserFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.RecursiveGroupTreeFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.ReviewFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.StaticMapResponseFakeBuilder;
import com.kbalazsworks.stackjudge.mocking.IdsWireMocker;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class CompanyService_getTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    private WireMockServer wireMockServer;

    @BeforeEach
    public void before()
    {
        wireMockServer = createStartAndGetIdsMockServer();
    }

    @AfterEach
    public void after()
    {
        wireMockServer.stop();
    }

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

    @Test
    public void oneParamMethodCallTest_calls2ParamsMethod()
    throws Exception
    {
        // Arrange
        CompanyService companyServiceMock = spy(serviceFactory.getCompanyService());
        CompanyGetServiceResponse returnedFake = new CompanyGetServiceResponse(
            new CompanyFakeBuilder().build(),
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );
        Company expectedCompany = new CompanyFakeBuilder().build();

        doReturn(returnedFake).when(companyServiceMock).get(CompanyFakeBuilder.defaultId1, List.of());

        // Act
        Company actualCompany = companyServiceMock.get(CompanyFakeBuilder.defaultId1);

        // Assert
        assertThat(actualCompany).usingRecursiveComparison().isEqualTo(expectedCompany);
    }

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
                    null,
                    new HashMap<>()
                )
            );
        }
        if (repetition == 2)
        {
            return new TestData(
                testedCompanyId,
                List.of((short) 1, (short) 2, (short) 3, (short) 5, (short) 6),
                new CompanyGetServiceResponse(
                    expectedCompany,
                    new CompanyStatistic(expectedCompany.getId(), 0, 1, 0, 0),
                    new RecursiveGroupTreeFakeBuilder().buildAsList(),
                    new CompanyAddresses(testedCompanyId, new AddressFakeBuilder().buildAsList()),
                    Map.of(
                        AddressFakeBuilder.defaultId1,
                        Map.of(MapPositionEnum.COMPANY_HEADER, new StaticMapResponseFakeBuilder().build(),
                            MapPositionEnum.COMPANY_LEFT, new StaticMapResponseFakeBuilder().build1_2()
                        )
                    ),
                    Map.of(expectedGroupId, new ReviewFakeBuilder().buildAsList()),
                    new CompanyOwners(CompanyFakeBuilder.defaultId1, List.of(IdsUserFakeBuilder.defaultId1)),
                    new IdsUserFakeBuilder().buildAsMap()
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
                    "classpath:test/sqls/preset_add_2_user.sql",
                    "classpath:test/sqls/preset_add_1_address.sql",
                    "classpath:test/sqls/preset_add_1_group.sql",
                    "classpath:test/sqls/preset_add_1_review.sql",
                    "classpath:test/sqls/preset_add_1_company_owner.sql",
                    "classpath:test/sqls/preset_add_1plus1_google_static_map_cache.sql"
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
    throws Exception
    {
        // Arrange - In preset
        IdsWireMocker.mockGetApiAccountList(wireMockServer);
        IdsWireMocker.mockPostConnectToken(wireMockServer);

        TestData tD = provider(repetitionInfo.getCurrentRepetition());

        // Act
        CompanyGetServiceResponse actualResponse = serviceFactory.getCompanyService().get(
            tD.testedCompanyId,
            tD.testedRequestRelationIds
        );

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(tD.expectedResponse);
    }
}
