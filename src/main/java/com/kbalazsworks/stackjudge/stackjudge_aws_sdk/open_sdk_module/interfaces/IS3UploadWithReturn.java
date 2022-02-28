package com.kbalazsworks.stackjudge.stackjudge_aws_sdk.open_sdk_module.interfaces;

import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.open_sdk_module.exceptions.OpenSdkResponseException;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.open_sdk_module.value_objects.OpenSdkStdResponse;
import com.kbalazsworks.stackjudge_aws_sdk.common.interfaces.IOpenSdkPostable;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.CdnServicePutResponse;

public interface IS3UploadWithReturn
{
    default String getApiUri()
    {
        return "/s3/upload";
    }

    OpenSdkStdResponse<CdnServicePutResponse> execute(IOpenSdkPostable postUploadRequest)
    throws OpenSdkResponseException;
}
