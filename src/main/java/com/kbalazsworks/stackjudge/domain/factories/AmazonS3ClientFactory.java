package com.kbalazsworks.stackjudge.domain.factories;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AmazonS3ClientFactory
{
    public AmazonS3 create(@NonNull BasicAWSCredentials credentials)
    {
        return AmazonS3ClientBuilder.standard()
            .withRegion(Regions.EU_CENTRAL_1)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();
    }
}
