package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.services.UrlService;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import lombok.NonNull;

import static org.mockito.Mockito.when;

public class UrlServiceMocker extends MockCreator
{
    public static UrlService generateCompanyOwnUrl_return_url(@NonNull String thanSecret, String returnUrl)
    {
        UrlService mock = getUrlService();
        when(mock.generateCompanyOwnUrl(thanSecret)).thenReturn(returnUrl);

        return mock;
    }
}
