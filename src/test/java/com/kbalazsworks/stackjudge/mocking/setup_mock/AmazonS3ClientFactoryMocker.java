package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.amazonaws.services.s3.AmazonS3;
import com.kbalazsworks.stackjudge.domain.factories.AmazonS3ClientFactory;
import com.kbalazsworks.stackjudge.mocking.MockCreator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AmazonS3ClientFactoryMocker extends MockCreator
{

    public static AmazonS3ClientFactory create_returns_AmazonS3Mock(AmazonS3 amazonS3Mock)
    {
        AmazonS3ClientFactory amazonS3ClientFactoryMock = getAmazonS3ClientFactoryMock();
        when(amazonS3ClientFactoryMock.create(any())).thenReturn(amazonS3Mock);

        return amazonS3ClientFactoryMock;
    }
}
