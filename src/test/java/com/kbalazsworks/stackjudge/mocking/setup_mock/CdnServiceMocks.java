package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.services.CdnService;
import com.kbalazsworks.stackjudge.domain.value_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import lombok.SneakyThrows;

import java.net.URL;

import static com.kbalazsworks.stackjudge.fake_builders.GoogleMapsUrlWithHashFakeBuilder.fakeGoogleMapsUrl;
import static org.mockito.Mockito.when;

public class CdnServiceMocks extends MockCreator
{
    @SneakyThrows
    public static CdnService put_returns_CdnServicePutResponse(
        CdnNamespaceEnum whenCdnNamespaceEnum,
        String whenFileName,
        String whenFileExtension,
        String thanFileName
    )
    {
        CdnService cdnServiceMock = getCdnServiceMock();
        when(cdnServiceMock.put(whenCdnNamespaceEnum, whenFileName, whenFileExtension, new URL(fakeGoogleMapsUrl)))
            .thenReturn(new CdnServicePutResponse(getPutObjectResultMock(), fakeGoogleMapsUrl, thanFileName));

        return cdnServiceMock;
    }
}
