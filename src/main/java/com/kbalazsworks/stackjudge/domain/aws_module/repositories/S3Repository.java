package com.kbalazsworks.stackjudge.domain.aws_module.repositories;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.kbalazsworks.stackjudge.domain.aws_module.factories.AmazonS3ClientFactory;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class S3Repository
{
    private final AmazonS3 client;

    @Autowired
    public S3Repository(
        ApplicationProperties applicationProperties,
        AmazonS3ClientFactory amazonS3ClientFactory
    )
    {
        this.client = amazonS3ClientFactory.create(
            new BasicAWSCredentials(
                applicationProperties.getAwsAccessKey(),
                applicationProperties.getAwsSecretKey()
            )
        );
    }

    public PutObjectResult put(@NonNull PutObjectRequest putObjectRequest)
    {
        return client.putObject(putObjectRequest);
    }
}
