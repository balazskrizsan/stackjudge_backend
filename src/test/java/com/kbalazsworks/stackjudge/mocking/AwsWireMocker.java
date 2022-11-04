package com.kbalazsworks.stackjudge.mocking;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.NonNull;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class AwsWireMocker
{
    public static void postS3Upload(@NonNull WireMockServer awsWireMockServer)
    {
        awsWireMockServer.stubFor(post("/s3/upload").willReturn(ok().withBody("{\"data\":{\"path\":\"mock/path\",\"fileName\":\"mockfile.name\",\"s3eTag\":\"s3etagQweasdzxc\",\"s3contentMd5\":\"md5asdqwezxc\"},\"success\": true,\"errorCode\":0,\"requestId\": \"0\"}")));
    }
}
