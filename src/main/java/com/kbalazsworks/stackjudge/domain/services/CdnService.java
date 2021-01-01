package com.kbalazsworks.stackjudge.domain.services;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.common.io.ByteSource;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.factories.LocalDateTimeFactory;
import com.kbalazsworks.stackjudge.domain.repositories.S3Repository;
import com.kbalazsworks.stackjudge.domain.value_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CdnService
{
    private S3Repository             s3Repository;
    private ApplicationProperties    applicationProperties;
    private LocalDateTimeFactory     localDateTimeFactory;
    private DateTimeFormatterService dateTimeFormatterService;

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

    @Autowired
    public void setLocalDateTimeFactory(LocalDateTimeFactory localDateTimeFactory)
    {
        this.localDateTimeFactory = localDateTimeFactory;
    }

    @Autowired
    public void setDateTimeFormatterService(DateTimeFormatterService dateTimeFormatterService)
    {
        this.dateTimeFormatterService = dateTimeFormatterService;
    }

    public CdnServicePutResponse put(
        CdnNamespaceEnum cdnNamespaceEnum,
        String fileName,
        String fileExtension,
        MultipartFile content
    )
    throws AmazonS3Exception
    {
        return put(cdnNamespaceEnum, "", fileName, fileExtension, content);
    }

    public CdnServicePutResponse put(
        CdnNamespaceEnum cdnNamespaceEnum,
        String subfolder,
        String fileName,
        String fileExtension,
        MultipartFile content
    ) throws AmazonS3Exception
    {
        try
        {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            ByteSource     contentBytes   = ByteSource.wrap(content.getBytes());
            objectMetadata.setContentLength(contentBytes.openStream().readAllBytes().length);

            long unixTimestamp = dateTimeFormatterService.toEpoch(localDateTimeFactory.create());

            String pathAndFile = cdnNamespaceEnum.getValue()
                + subfolder
                + "/" + fileName + "-" + unixTimestamp + "." + fileExtension;

            return new CdnServicePutResponse(
                s3Repository.put(
                    new PutObjectRequest(
                        applicationProperties.getAwsS3CdnBucket(),
                        pathAndFile,
                        contentBytes.openStream(),
                        objectMetadata
                    )
                ),
                pathAndFile
            );
        }
        catch (IOException e)
        {
            throw new AmazonS3Exception("File upload error.");
        }
    }
}
