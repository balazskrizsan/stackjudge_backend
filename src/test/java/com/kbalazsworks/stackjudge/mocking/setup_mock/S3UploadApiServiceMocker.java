package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.s3.upload.S3UploadApiService;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.s3.upload.V2S3UploadApiService;
import com.kbalazsworks.stackjudge_aws_sdk.common.entities.StdResponse;
import com.kbalazsworks.stackjudge_aws_sdk.common.exceptions.ResponseException;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PostUploadRequest;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PutAndSaveResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class S3UploadApiServiceMocker extends MockCreator
{
    public static V2S3UploadApiService execute_returns(
        PostUploadRequest whenPostUploadRequest,
        StdResponse<PutAndSaveResponse> thanReturn
    ) throws ResponseException
    {
        V2S3UploadApiService mock = getV2S3UploadApiService();
        when(mock.post(any())).thenReturn(thanReturn);

        return mock;
    }
}
