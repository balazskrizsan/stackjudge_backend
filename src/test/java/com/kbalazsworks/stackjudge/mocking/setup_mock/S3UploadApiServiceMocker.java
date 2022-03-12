package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.s3.upload.S3UploadApiService;
import com.kbalazsworks.stackjudge_aws_sdk.common.entities.StdResponse;
import com.kbalazsworks.stackjudge_aws_sdk.common.exceptions.ResponseException;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PostUploadRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class S3UploadApiServiceMocker extends MockCreator
{
    public static S3UploadApiService execute_returns(
        PostUploadRequest whenPostUploadRequest,
        StdResponse<CdnServicePutResponse> thanReturn
    ) throws ResponseException
    {
        S3UploadApiService mock = getS3UploadApiService();
        when(mock.execute(any())).thenReturn(thanReturn);

        return mock;
    }
}
