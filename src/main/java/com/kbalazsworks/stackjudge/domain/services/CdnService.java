package com.kbalazsworks.stackjudge.domain.services;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.google.common.io.ByteSource;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.repositories.S3Repository;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CdnService
{
    private S3Repository s3Repository;
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

    public PutObjectResult put(CdnNamespaceEnum cdnNamespaceEnum, String fileName, MultipartFile content)
    throws AmazonS3Exception
    {
        return put(cdnNamespaceEnum, "", fileName, content);
    }

    public PutObjectResult put(
        CdnNamespaceEnum cdnNamespaceEnum,
        String subfolder,
        String fileName,
        MultipartFile content
    ) throws AmazonS3Exception
    {
        try
        {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(ByteSource.wrap(content.getBytes()).openStream().readAllBytes().length);

            return s3Repository.put(
                new PutObjectRequest(
                    applicationProperties.getAwsS3CdnBucket(),
                    cdnNamespaceEnum.getValue() + subfolder + "/" + fileName,
                    ByteSource.wrap(content.getBytes()).openStream(),
                    objectMetadata
                )
            );
        }
        catch (IOException e)
        {
            throw new AmazonS3Exception("File upload error.");
        }
    }
}
