package com.kbalazsworks.stackjudge.domain.services;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.common.io.ByteSource;
import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.factories.LocalDateTimeFactory;
import com.kbalazsworks.stackjudge.domain.repositories.S3Repository;
import com.kbalazsworks.stackjudge.domain.value_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class CdnService
{
    private final ApplicationProperties    applicationProperties;
    private final LocalDateTimeFactory     localDateTimeFactory;
    private final DateTimeFormatterService dateTimeFormatterService;
    private final S3Repository             s3Repository;

    public CdnServicePutResponse put(
        @NonNull CdnNamespaceEnum cdnNamespaceEnum,
        @NonNull String fileName,
        @NonNull String fileExtension,
        @NonNull MultipartFile content
    ) throws AmazonS3Exception, ContentReadException
    {
        return put(cdnNamespaceEnum, "", fileName, fileExtension, content);
    }

    public CdnServicePutResponse put(
        @NonNull CdnNamespaceEnum cdnNamespaceEnum,
        @NonNull String subfolder,
        @NonNull String fileName,
        @NonNull String fileExtension,
        @NonNull MultipartFile content
    ) throws AmazonS3Exception, ContentReadException
    {
        byte[] contentBytes;
        try
        {
            contentBytes = content.getBytes();
        }
        catch (IOException e)
        {
            throw new ContentReadException("MultipartFile content error: " + e.getMessage());
        }

        return put(cdnNamespaceEnum, subfolder, fileName, fileExtension, ByteSource.wrap(contentBytes));
    }

    // @todo: test
    public CdnServicePutResponse put(
        @NonNull CdnNamespaceEnum cdnNamespaceEnum,
        @NonNull String fileName,
        @NonNull String fileExtension,
        @NonNull URL content
    ) throws AmazonS3Exception, ContentReadException
    {
        byte[] contentBytes;

        try
        {
            contentBytes = content.openStream().readAllBytes();
        }
        catch (IOException e)
        {
            throw new ContentReadException("URL content error: " + e.getMessage());
        }

        return put(cdnNamespaceEnum, "", fileName, fileExtension, ByteSource.wrap(contentBytes));
    }

    // @todo: test
    public CdnServicePutResponse put(
        @NonNull CdnNamespaceEnum cdnNamespaceEnum,
        @NonNull String fileName,
        @NonNull String fileExtension,
        @NonNull ByteSource content
    ) throws AmazonS3Exception
    {
        return put(cdnNamespaceEnum, "", fileName, fileExtension, content);
    }

    // @todo: test
    public CdnServicePutResponse put(
        @NonNull CdnNamespaceEnum cdnNamespaceEnum,
        @NonNull String subfolder,
        @NonNull String fileName,
        @NonNull String fileExtension,
        @NonNull ByteSource contentBytes
    ) throws AmazonS3Exception
    {
        String pathAndFile = "";
        try
        {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(contentBytes.openStream().readAllBytes().length);

            long unixTimestamp = dateTimeFormatterService.toEpoch(localDateTimeFactory.create());

            String fullFileName = "/" + fileName + "-" + unixTimestamp + "." + fileExtension;

            pathAndFile = cdnNamespaceEnum.getValue() + subfolder + fullFileName;

            CdnServicePutResponse response = new CdnServicePutResponse(
                s3Repository.put(
                    new PutObjectRequest(
                        applicationProperties.getAwsS3CdnBucket(),
                        pathAndFile,
                        contentBytes.openStream(),
                        objectMetadata
                    )
                ),
                pathAndFile,
                fullFileName
            );

            log.info("Successful AWS S3 upload: " + response.path());

            return response;
        }
        catch (IOException e)
        {
            throw new AmazonS3Exception("AWS S3 upload error on: " + pathAndFile);
        }
    }
}
