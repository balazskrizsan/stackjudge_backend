package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.aws_module.enums.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.domain.aws_module.services.CdnService;
import com.kbalazsworks.stackjudge.domain.value_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import lombok.SneakyThrows;

import java.net.URL;

import static com.kbalazsworks.stackjudge.fake_builders.GoogleMapsUrlWithHashFakeBuilder.fakeGoogleMapsUrl;
import static org.mockito.Mockito.when;

public class CdnServiceMocker extends MockCreator
{
    @SneakyThrows
    public static CdnService put_returns_CdnServicePutResponse(
        CdnNamespaceEnum whenCdnNamespaceEnum,
        String whenFileName,
        String whenFileExtension,
        String thanFileName
    )
    {
        CdnService mock = getCdnServiceMock();
        when(mock.put(whenCdnNamespaceEnum, whenFileName, whenFileExtension, new URL(fakeGoogleMapsUrl)))
            .thenReturn(new CdnServicePutResponse(getPutObjectResultMock(), fakeGoogleMapsUrl, thanFileName));

        return mock;
    }
}
