package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.RemoteFile;

import java.util.UUID;

public class RemoteFileFakeBuilder
{
    private UUID   id           = UUID.fromString("00000000-0000-0000-0000-000000106001");
    private String path         = "path";
    private String filename     = "filename";
    private String s3ETag       = "eTag";
    private String s3ContentMd5 = "contentMd5";

    public RemoteFile build()
    {
        return new RemoteFile(id, path, filename, s3ETag, s3ContentMd5);
    }
}
