package com.kbalazsworks.stackjudge.fake_builders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.CdnServicePutResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
public class CdnServicePutResponseFakeBuilder
{
    @JsonProperty("path")
    private String path         = GoogleMapsUrlWithHashFakeBuilder.fakeGoogleMapsUrl;
    @JsonProperty("fileName")
    private String fileName     = GoogleStaticMapsCacheFakeBuilder.hash;
    @JsonProperty("s3eTag")
    private String s3eTag       = "eTag";
    @JsonProperty("s3contentMd5")
    private String s3contentMd5 = "contentMd5";

    public CdnServicePutResponse build()
    {
        return new CdnServicePutResponse(
            path,
            fileName,
            s3eTag,
            s3contentMd5
        );
    }
}
