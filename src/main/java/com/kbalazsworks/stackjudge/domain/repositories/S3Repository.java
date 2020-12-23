package com.kbalazsworks.stackjudge.domain.repositories;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class S3Repository
{
    private final AmazonS3 client;

    @Autowired
    public S3Repository(ApplicationProperties applicationProperties)
    {
        BasicAWSCredentials credentials = new BasicAWSCredentials(
            applicationProperties.getAwsAccessKey(),
            applicationProperties.getAwsSecretKey()
        );

        this.client = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.EU_CENTRAL_1)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();
    }

    public PutObjectResult put(PutObjectRequest putObjectRequest)
    {
        return client.putObject(putObjectRequest);
    }
}
