package com.kbalazsworks.stackjudge.stackjudge_aws_sdk.s3.upload;

import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.services.AwsOpenSdkService;
import com.kbalazsworks.stackjudge_aws_sdk.common.interfaces.IOpenSdkPostable;
import com.kbalazsworks.stackjudge_aws_sdk.schema_interfaces.IS3Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3UploadApiService implements IS3Upload
{
    private final AwsOpenSdkService awsOpenSdkService;

    @Override
    public void execute(IOpenSdkPostable postUploadRequest)
    {
        awsOpenSdkService.post(postUploadRequest, getApiUri());
    }
}
