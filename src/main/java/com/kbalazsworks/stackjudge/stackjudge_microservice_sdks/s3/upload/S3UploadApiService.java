package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.s3.upload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services.AwsOpenSdkService;
import com.kbalazsworks.stackjudge_aws_sdk.common.entities.StdResponse;
import com.kbalazsworks.stackjudge_aws_sdk.common.exceptions.ResponseException;
import com.kbalazsworks.stackjudge_aws_sdk.common.interfaces.IOpenSdkPostable;
import com.kbalazsworks.stackjudge_aws_sdk.schema_interfaces.IS3Upload;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.ApiResponseDataCdnServicePutResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.CdnServicePutResponse;
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
public class S3UploadApiService implements IS3Upload
{
    private final AwsOpenSdkService awsOpenSdkService;
    private final ObjectMapper      objectMapper = new ObjectMapper();

    @Override
    public StdResponse<CdnServicePutResponse> post(IOpenSdkPostable postUploadRequest) throws ResponseException
    {
        try
        {
            ResponseEntity<String> response = awsOpenSdkService.post(postUploadRequest, getApiUri());
            log.info("Api response: {}", response.getBody());

            ApiResponseDataCdnServicePutResponse body = objectMapper.readValue(
                response.getBody(),
                ApiResponseDataCdnServicePutResponse.class
            );

            return new StdResponse<>(response.getStatusCode(), response.getHeaders(),body.getCdnServicePutResponse());
        }
        catch (Exception e)
        {
            log.error("Api response error: {}", e.getMessage(), e);

            throw new ResponseException("Invalid service response");
        }
    }

    @Async
    @Override
    public Future<StdResponse<CdnServicePutResponse>> postAsync(IOpenSdkPostable postUploadRequest)
    throws ResponseException
    {
        throw new NotImplementedException();
    }
}
