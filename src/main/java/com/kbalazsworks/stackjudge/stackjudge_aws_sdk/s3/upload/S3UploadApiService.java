package com.kbalazsworks.stackjudge.stackjudge_aws_sdk.s3.upload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.services.AwsOpenSdkService;
import com.kbalazsworks.stackjudge_aws_sdk.common.entities.StdResponse;
import com.kbalazsworks.stackjudge_aws_sdk.common.exceptions.ResponseException;
import com.kbalazsworks.stackjudge_aws_sdk.common.interfaces.IOpenSdkPostable;
import com.kbalazsworks.stackjudge_aws_sdk.schema_interfaces.IS3UploadWithReturn;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.ApiResponseDataCdnServicePutResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.CdnServicePutResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class S3UploadApiService implements IS3UploadWithReturn
{
    private final AwsOpenSdkService awsOpenSdkService;
    private final ObjectMapper      objectMapper = new ObjectMapper();

    @Override
    public StdResponse<CdnServicePutResponse> execute(IOpenSdkPostable postUploadRequest)
    throws ResponseException
    {
        try
        {
            ResponseEntity<String> response = awsOpenSdkService.post(postUploadRequest, getApiUri());
            log.info("Api response: {}", response.getBody());

            ApiResponseDataCdnServicePutResponse body = objectMapper.readValue(
                response.getBody(),
                ApiResponseDataCdnServicePutResponse.class
            );

            return new StdResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                body.getCdnServicePutResponse()
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ResponseException("Invalid service response");
        }
    }
}
