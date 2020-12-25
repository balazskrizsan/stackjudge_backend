package com.kbalazsworks.stackjudge.integration.domain.services.company_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.db.tables.records.AddressRecord;
import com.kbalazsworks.stackjudge.db.tables.records.CompanyRecord;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.AddressException;
import com.kbalazsworks.stackjudge.domain.services.AddressService;
import com.kbalazsworks.stackjudge.domain.services.CdnService;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import com.kbalazsworks.stackjudge.integration.domain.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.integration.domain.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.integration.domain.mock_factories.CdnServiceMockFactory;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.commons.JUnitException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class CompanyServiceCreateTest extends AbstractIntegrationTest
{
    @Autowired
    private CompanyService companyService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CdnService cdnService;

    @BeforeEach
    public void setUp()
    {
        companyService.setAddressService(addressService);
        companyService.setCdnService(cdnService);
    }

    private record TestData(
        Company testedCompany,
        Address testedAddress,
        MultipartFile testedFile,
        long expectedCompanyId,
        Company expectedCompany,
        long expectedAddressId,
        Address expectedAddress,
        Executable cdnCallVerification
    )
    {
    }

    private TestData providerFor_insertOneCompanyWithOneAddress_checkByProvider(
        int repetition,
        CdnService cdnServiceMock
    )
    {
        if (repetition == 1)
        {
            return new TestData(
                new CompanyFakeBuilder().build(),
                new AddressFakeBuilder().build(),
                null,
                23455487L,
                new CompanyFakeBuilder().setId(23455487L).build(),
                24562647L,
                new AddressFakeBuilder()
                    .setId(24562647L)
                    .setCompanyId(23455487L)
                    .build(),
                () -> verify(cdnServiceMock, never()).put(any(), any(), any())
            );
        }
        if (repetition == 2)
        {
            MockMultipartFile testFile = new MockMultipartFile("a", new byte[]{'a'});

            return new TestData(
                new CompanyFakeBuilder().build(),
                new AddressFakeBuilder().build(),
                testFile,
                23455487L,
                new CompanyFakeBuilder().setId(23455487L).build(),
                24562647L,
                new AddressFakeBuilder()
                    .setId(24562647L)
                    .setCompanyId(23455487L)
                    .build(),
                () -> verify(cdnServiceMock, times(1))
                    .put(eq(CdnNamespaceEnum.COMPANY_LOGOS), matches("\\d+.jpg"), eq(testFile))
            );
        }

        throw new JUnitException("Missing test data on repetition#" + repetition);
    }

    @RepeatedTest(2)
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
    public void insertOneCompanyWithOneAddress_checkByProvider(RepetitionInfo repetitionInfo)
    {
        // Arrange
        CdnService cdnServiceMock = CdnServiceMockFactory.createMock();
        TestData testData = providerFor_insertOneCompanyWithOneAddress_checkByProvider(
            repetitionInfo.getCurrentRepetition(),
            cdnServiceMock
        );
        companyService.setCdnService(cdnServiceMock);

        // Act
        companyService.create(testData.testedCompany, testData.testedAddress, testData.testedFile);

        // Assert
        CompanyRecord actualCompany = getQueryBuilder().selectFrom(companyTable).fetchOne();
        actualCompany.setId(testData.expectedCompanyId);

        AddressRecord actualAddress = getQueryBuilder().selectFrom(addressTable).fetchOne();
        actualAddress.setId(testData.expectedAddressId);
        actualAddress.setCompanyId(testData.expectedCompanyId);

        assertAll(
            () -> assertThat(actualCompany.into(Company.class)).isEqualTo(testData.expectedCompany),
            () -> assertThat(actualAddress.into(Address.class)).isEqualTo(testData.expectedAddress),
            testData::cdnCallVerification
        );
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
    public void insertOneCompanyWithErrorOnAddress_rollbackShouldClearTheCompany()
    {
        // Arrange
        Company testedCompany = new CompanyFakeBuilder().build();
        Address testedAddress = new AddressFakeBuilder().build();

        AddressService addressServiceMock = mock(AddressService.class);
        doThrow(AddressException.class).when(addressServiceMock).create(Mockito.any());

        companyService.setAddressService(addressServiceMock);

        // Act - Assert
        CompanyRecord actualCompany = getQueryBuilder().selectFrom(companyTable).fetchOne();
        AddressRecord actualAddress = getQueryBuilder().selectFrom(addressTable).fetchOne();

        assertAll(
            () -> assertThatThrownBy(() -> companyService.create(testedCompany, testedAddress, null))
                .isInstanceOf((AddressException.class)),
            () -> assertThat(actualCompany).isNull(),
            () -> assertThat(actualAddress).isNull()
        );
    }
}
