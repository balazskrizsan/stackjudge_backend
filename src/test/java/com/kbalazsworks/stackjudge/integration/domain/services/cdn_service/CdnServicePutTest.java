package com.kbalazsworks.stackjudge.integration.domain.services.cdn_service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.factories.AmazonS3ClientFactory;
import com.kbalazsworks.stackjudge.domain.repositories.S3Repository;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        String            testedBucket           = "test-aws-bucket";
        String            expectedKey            = "company-logos/test-1609459262.jpg";
        String            expectedBucketName     = "test-aws-bucket";
        byte[]            expectedInput          = new byte[]{'a', 'b', 'c'};
        int               expectedContentLength  = 3;

        AmazonS3ClientFactory amazonS3ClientFactoryMock = mock(AmazonS3ClientFactory.class);

        AmazonS3 amazonS3Mock = mock(AmazonS3.class);
        when(amazonS3ClientFactoryMock.create(any())).thenReturn(amazonS3Mock);

        ApplicationProperties applicationPropertiesMock = mock(ApplicationProperties.class);
        when(applicationPropertiesMock.getAwsAccessKey()).thenReturn("aaa");
        when(applicationPropertiesMock.getAwsSecretKey()).thenReturn("bbb");
        when(applicationPropertiesMock.getAwsS3CdnBucket()).thenReturn(testedBucket);

        // Act
        serviceFactory
            .getCdnService(
                applicationPropertiesMock,
                MockFactory.getLocalDateTimeFactoryMockWithDateTime(testMockTime),
                null,
                new S3Repository(applicationPropertiesMock, amazonS3ClientFactoryMock)
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
