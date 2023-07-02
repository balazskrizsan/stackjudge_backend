package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PutAndSaveResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.RemoteFile;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
public class PutAndSaveResponseFakeBuilder
{
    private RemoteFile remoteFile = new RemoteFileFakeBuilder().build();

    public PutAndSaveResponse build()
    {
        return new PutAndSaveResponse(remoteFile);
    }
}
