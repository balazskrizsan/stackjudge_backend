package com.kbalazsworks.stackjudge.oidc.factories;

import com.kbalazsworks.stackjudge.oidc.entities.OidcConfig;
import com.kbalazsworks.stackjudge.oidc.exceptions.OidcApiException;
import com.kbalazsworks.stackjudge.oidc.services.OidcHttpClientService;
import com.kbalazsworks.stackjudge.oidc.services.OidcService;
import com.kbalazsworks.stackjudge.oidc.services.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class OidcServiceFactory
{
    private final OidcHttpClientService oidcHttpClientService;
    private final TokenService      tokenService;
    private final OidcSystemFactory oidcSystemFactory;

    private static final String DISCOVERY_ENDPOINT = "/.well-known/openid-configuration";

    public OidcService create(String host) throws OidcApiException
    {
        OidcConfig oidcConfig = oidcHttpClientService.get(host + DISCOVERY_ENDPOINT, OidcConfig.class);

        return new OidcService(oidcConfig, tokenService, oidcHttpClientService, oidcSystemFactory);
    }
}
