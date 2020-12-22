package com.kbalazsworks.stackjudge.domain.services;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.repositories.S3Repository;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CdnService
{
    private S3Repository          s3Repository;
    private ApplicationProperties applicationProperties;

    @Autowired
    public void setS3Repository(S3Repository s3Repository)
    {
        this.s3Repository = s3Repository;
    }

    @Autowired
    public void setApplicationProperties(ApplicationProperties applicationProperties)
    {
        this.applicationProperties = applicationProperties;
    }

    public void put(CdnNamespaceEnum cdnNamespaceEnum, String fileName, String content)
    {
        put(cdnNamespaceEnum, "", fileName, content);
    }

    public void put(CdnNamespaceEnum cdnNamespaceEnum, String subfolder, String fileName, String content)
    {
        s3Repository.put(
            new PutObjectRequest(
                applicationProperties.getAwsS3CdnBucket(),
                cdnNamespaceEnum.getValue() + subfolder + "/" + fileName,
                content
            )
        );
    }
}
