package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.s3.upload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services.AwsOpenSdkService;
import com.kbalazsworks.stackjudge_aws_sdk.common.entities.StdResponse;
import com.kbalazsworks.stackjudge_aws_sdk.common.exceptions.ResponseException;
import com.kbalazsworks.stackjudge_aws_sdk.common.interfaces.IOpenSdkPostable;
import com.kbalazsworks.stackjudge_aws_sdk.schema_interfaces.IV2S3Upload;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.ApiResponseDataPutAndSaveResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PutAndSaveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
@Log4j2
public class V2S3UploadApiService implements IV2S3Upload
{
    private final AwsOpenSdkService awsOpenSdkService;
    private final ObjectMapper      objectMapper = new ObjectMapper();

    @Override
    public StdResponse<PutAndSaveResponse> post(IOpenSdkPostable postUploadRequest) throws ResponseException
    {
        try
        {
            log.info("S3 upload post: {}", getApiUri());
            ResponseEntity<String> response = awsOpenSdkService.post(postUploadRequest, getApiUri());
            log.info("Response: {}", response.getBody());

            ApiResponseDataPutAndSaveResponse body = objectMapper.readValue(
                response.getBody(),
                ApiResponseDataPutAndSaveResponse.class
            );

            return new StdResponse<>(response.getStatusCode(), response.getHeaders(), body.getData());
        }
        catch (Exception e)
        {
            log.error("Post error: {}", e.getMessage(), e);

            throw new ResponseException("Invalid service response");
        }
    }

    @Async
    @Override
    public Future<StdResponse<PutAndSaveResponse>> postAsync(IOpenSdkPostable postUploadRequest)
    {
        throw new NotImplementedException();
    }
}
