package com.kbalazsworks.stackjudge.domain.aws_module.value_objects;

import com.amazonaws.services.s3.model.PutObjectResult;

public record CdnServicePutResponse(PutObjectResult putObjectResult, String path, String fileName)
{
}