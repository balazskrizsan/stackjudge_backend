package com.kbalazsworks.stackjudge.stackjudge_aws_sdk.s3.upload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.open_sdk_module.exceptions.OpenSdkResponseException;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.open_sdk_module.interfaces.IS3UploadWithReturn;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.open_sdk_module.value_objects.OpenSdkStdResponse;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.services.AwsOpenSdkService;
import com.kbalazsworks.stackjudge_aws_sdk.common.interfaces.IOpenSdkPostable;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.ApiResponseDataCdnServicePutResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.CdnServicePutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3UploadApiService implements IS3UploadWithReturn
{
    private final AwsOpenSdkService awsOpenSdkService;
    private final ObjectMapper      objectMapper = new ObjectMapper();

    @Override
    public OpenSdkStdResponse<CdnServicePutResponse> execute(IOpenSdkPostable postUploadRequest)
    throws OpenSdkResponseException
    {
        try
        {
            ResponseEntity<String> response = awsOpenSdkService.post(postUploadRequest, getApiUri());

            ApiResponseDataCdnServicePutResponse body = objectMapper.readValue(
                response.getBody(),
                ApiResponseDataCdnServicePutResponse.class
            );

            return new OpenSdkStdResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                body.CdnServicePutResponse()
            );
        }
        catch (Exception e)
        {
            throw new OpenSdkResponseException("Invalid service reponse");
        }
    }
}

