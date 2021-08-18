package com.kbalazsworks.stackjudge.integration.domain.services.cdn_service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.repositories.S3Repository;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.mocking.setup_mock.AmazonS3ClientFactoryMocker;
import com.kbalazsworks.stackjudge.mocking.setup_mock.ApplicationPropertiesMocker;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;

public class CdnServicePutTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Captor
    ArgumentCaptor<PutObjectRequest> insertValidPutObjectRequest_perfect_captor;

    @Test
    public void insertValidPutObjectRequest_perfect() throws ContentReadException
    {
        // Arrange
        String            testMockTime           = "2021-01-01 00:01:02";
        CdnNamespaceEnum  testedCdnNamespaceEnum = CdnNamespaceEnum.COMPANY_LOGOS;
        String            testedFileName         = "test";
        String            testedFileExtension    = "jpg";
        MockMultipartFile testedFile             = new MockMultipartFile("a", new byte[]{'a', 'b', 'c'});
        String            expectedKey            = "company-logos/test-1609459262.jpg";
        String            expectedBucketName     = ApplicationPropertiesMocker.AWS_S3_CDN_BUCKET;
        byte[]            expectedInput          = new byte[]{'a', 'b', 'c'};
        int               expectedContentLength  = 3;
        AmazonS3          amazonS3Mock           = MockCreator.getAmazonS3Mock();

        // Act
        serviceFactory
            .getCdnService(
                ApplicationPropertiesMocker.getDefaultMock(),
                MockFactory.getLocalDateTimeFactoryMockWithDateTime(testMockTime),
                null,
                new S3Repository(
                    ApplicationPropertiesMocker.getDefaultMock(),
                    AmazonS3ClientFactoryMocker.create_returns_AmazonS3Mock(amazonS3Mock)
                )
            )
            .put(testedCdnNamespaceEnum, testedFileName, testedFileExtension, testedFile);

        // Assert
        verify(amazonS3Mock).putObject(insertValidPutObjectRequest_perfect_captor.capture());
        PutObjectRequest resultPutObjectRequest = insertValidPutObjectRequest_perfect_captor.getValue();

        assertAll(
            () -> assertThat(resultPutObjectRequest.getKey()).isEqualTo(expectedKey),
            () -> assertThat(resultPutObjectRequest.getBucketName()).isEqualTo(expectedBucketName),
            () -> assertThat(resultPutObjectRequest.getInputStream().readAllBytes()).isEqualTo(expectedInput),
            () -> assertThat(resultPutObjectRequest.getMetadata().getContentLength()).isEqualTo(expectedContentLength)
        );
    }
}
