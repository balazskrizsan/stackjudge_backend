package com.kbalazsworks.stackjudge.integration.domain.company_module.services;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.db.tables.records.AddressRecord;
import com.kbalazsworks.stackjudge.db.tables.records.CompanyRecord;
import com.kbalazsworks.stackjudge.domain.address_module.entities.Address;
import com.kbalazsworks.stackjudge.domain.company_module.entities.Company;
import com.kbalazsworks.stackjudge.domain.aws_module.enums.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.address_module.exceptions.AddressHttpException;
import com.kbalazsworks.stackjudge.domain.aws_module.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.aws_module.services.CdnService;
import com.kbalazsworks.stackjudge.domain.company_module.services.CompanyService;
import com.kbalazsworks.stackjudge.domain.aws_module.value_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.integration.annotations.TruncateAllTables;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.mocking.setup_mock.AddressServiceMocker;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class CompanyService_createTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

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

    private TestData provider(int repetition, CdnService cdnServiceMock) throws ContentReadException
    {
        if (repetition == 1)
        {
            return new TestData(
                new CompanyFakeBuilder().build(),
                new AddressFakeBuilder().build(),
                null,
                new CompanyFakeBuilder().build(),
                new AddressFakeBuilder().build(),
                () -> verify(cdnServiceMock, never()).put(any(), any(), any(), any(MultipartFile.class))
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
                .thenReturn(new CdnServicePutResponse(new PutObjectResult(), "fake-path/123.jpg", "123.jpg"));

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
    public void insertOneCompanyWithOneAddress_checkByProvider(RepetitionInfo repetitionInfo) throws ContentReadException
    {
        // Arrange
        CdnService cdnServiceMock = MockCreator.getCdnServiceMock();
        TestData   testData       = provider(repetitionInfo.getCurrentRepetition(), cdnServiceMock);

        // Act
        serviceFactory.getCompanyService(
            null,
            null,
            null,
            null,
            null,
            cdnServiceMock,
            null,
            null,
            null,
            null,
            null,
            null
        ).create(testData.testedCompany, testData.testedAddress, testData.testedFile);

        // Assert
        CompanyRecord actualCompany = getQueryBuilder().selectFrom(companyTable).fetchOne();
        actualCompany.setId(testData.expectedCompany.getId());

        AddressRecord actualAddress = getQueryBuilder().selectFrom(addressTable).fetchOne();
        actualAddress.setId(testData.expectedAddress.id());
        actualAddress.setCompanyId(testData.expectedAddress.companyId());

        assertAll(
            () -> assertThat(actualCompany.into(Company.class))
                .usingRecursiveComparison()
                .isEqualTo(testData.expectedCompany),
            () -> assertThat(actualAddress.into(Address.class))
                .usingRecursiveComparison()
                .isEqualTo(testData.expectedAddress),
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

        CompanyService service = serviceFactory.getCompanyService(
            AddressServiceMocker.create_throws_AddressHttpException(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );

        // Act - Assert
        CompanyRecord actualCompany = getQueryBuilder().selectFrom(companyTable).fetchOne();
        AddressRecord actualAddress = getQueryBuilder().selectFrom(addressTable).fetchOne();

        assertAll(
            () -> assertThatThrownBy(() -> service.create(testedCompany, testedAddress, null))
                .isInstanceOf((AddressHttpException.class)),
            () -> assertThat(actualCompany).isNull(),
            () -> assertThat(actualAddress).isNull()
        );
    }
}
