package com.kbalazsworks.stackjudge.integration.domain.services.company_service;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.db.tables.records.AddressRecord;
import com.kbalazsworks.stackjudge.db.tables.records.CompanyRecord;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.AddressHttpException;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.services.*;
import com.kbalazsworks.stackjudge.domain.services.company_services.SearchService;
import com.kbalazsworks.stackjudge.domain.value_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge.integration.annotations.TruncateAllTables;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.commons.JUnitException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class CompanyServiceCreateTest extends AbstractIntegrationTest
{
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AddressService    addressService;
    @Autowired
    private PaginatorService  paginatorService;
    @Autowired
    private JooqService       jooqService;
    @Autowired
    private CdnService        cdnService;
    @Autowired
    private SearchService     searchService;
    @Autowired
    private CompanyService    companyService;

    @BeforeEach
    @AfterEach
    public void clean()
    {
        companyService.setCompanyRepository(companyRepository);
        companyService.setAddressService(addressService);
        companyService.setPaginatorService(paginatorService);
        companyService.setJooqService(jooqService);
        companyService.setCdnService(cdnService);
        companyService.setSearchService(searchService);
    }

    private record TestData(
        Company testedCompany,
        Address testedAddress,
        MultipartFile testedFile,
        Company expectedCompany,
        Address expectedAddress,
        Executable cdnCallVerification
    )
    {
    }

    private TestData provider(int repetition, CdnService cdnServiceMock)
    {
        if (repetition == 1)
        {
            return new TestData(
                new CompanyFakeBuilder().build(),
                new AddressFakeBuilder().build(),
                null,
                new CompanyFakeBuilder().build(),
                new AddressFakeBuilder().build(),
                () -> verify(cdnServiceMock, never()).put(any(), any(), any(), any())
            );
        }

        if (repetition == 2)
        {
            MockMultipartFile testFile = new MockMultipartFile("a", new byte[]{'a'});

            when(
                cdnServiceMock.put(
                    eq(CdnNamespaceEnum.COMPANY_LOGOS),
                    matches("\\d+"),
                    eq("jpg"),
                    eq(testFile)
                )
            )
                .thenReturn(new CdnServicePutResponse(new PutObjectResult(), "fake-path/123.jpg"));

            return new TestData(
                new CompanyFakeBuilder().logoPath("").build(),
                new AddressFakeBuilder().build(),
                testFile,
                new CompanyFakeBuilder().logoPath("fake-path/123.jpg").build(),
                new AddressFakeBuilder().build(),
                () -> verify(cdnServiceMock, times(1))
                    .put(eq(CdnNamespaceEnum.COMPANY_LOGOS), matches("\\d+"), eq("jpg"), eq(testFile))
            );
        }

        throw new JUnitException("Missing test data on repetition#" + repetition);
    }

    @RepeatedTest(2)
    @TruncateAllTables
    public void insertOneCompanyWithOneAddress_checkByProvider(RepetitionInfo repetitionInfo)
    {
        // Arrange
        CdnService cdnServiceMock = mock(CdnService.class);
        TestData testData = provider(repetitionInfo.getCurrentRepetition(), cdnServiceMock);
        companyService.setCdnService(cdnServiceMock);

        // Act
        companyService.create(testData.testedCompany, testData.testedAddress, testData.testedFile);

        // Assert
        CompanyRecord actualCompany = getQueryBuilder().selectFrom(companyTable).fetchOne();
        actualCompany.setId(testData.expectedCompany.id());

        AddressRecord actualAddress = getQueryBuilder().selectFrom(addressTable).fetchOne();
        actualAddress.setId(testData.expectedAddress.id());
        actualAddress.setCompanyId(testData.expectedAddress.companyId());

        assertAll(
            () -> assertThat(actualCompany.into(Company.class)).isEqualTo(testData.expectedCompany),
            () -> assertThat(actualAddress.into(Address.class)).isEqualTo(testData.expectedAddress),
            testData::cdnCallVerification
        );
    }

    @Test
    @TruncateAllTables
    public void insertOneCompanyWithErrorOnAddress_rollbackShouldClearTheCompany()
    {
        // Arrange
        Company testedCompany = new CompanyFakeBuilder().build();
        Address testedAddress = new AddressFakeBuilder().build();

        AddressService addressServiceMock = mock(AddressService.class);
        doThrow(AddressHttpException.class).when(addressServiceMock).create(Mockito.any());

        companyService.setAddressService(addressServiceMock);

        // Act - Assert
        CompanyRecord actualCompany = getQueryBuilder().selectFrom(companyTable).fetchOne();
        AddressRecord actualAddress = getQueryBuilder().selectFrom(addressTable).fetchOne();

        assertAll(
            () -> assertThatThrownBy(() -> companyService.create(testedCompany, testedAddress, null))
                .isInstanceOf((AddressHttpException.class)),
            () -> assertThat(actualCompany).isNull(),
            () -> assertThat(actualAddress).isNull()
        );

        clean();
    }
}
