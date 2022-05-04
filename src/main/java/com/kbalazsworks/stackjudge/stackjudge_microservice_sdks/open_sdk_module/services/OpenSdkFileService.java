package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class OpenSdkFileService
{
    public HttpEntity<ByteArrayResource> createByteArrayResourceEntityFromString(
        byte[] content,
        String fileNameAndExtension
    )
    {
        return createByteArrayResourceEntityFromString(content, fileNameAndExtension, MediaType.TEXT_PLAIN);
    }

    public HttpEntity<ByteArrayResource> createByteArrayResourceEntityFromString(
        byte[] content,
        String fileNameAndExtension,
        MediaType contentType
    )
    {
        HttpHeaders fileHeader = new HttpHeaders();
        fileHeader.setContentType(contentType);
        final ByteArrayResource byteArrayResource = new ByteArrayResource(content)
        {
            @Override
            public String getFilename()
            {
                return fileNameAndExtension;
            }
        };

        return new HttpEntity<>(byteArrayResource, fileHeader);
    }
}
