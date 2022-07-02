package com.kbalazsworks.stackjudge.oidc.factories;

import com.kbalazsworks.stackjudge.oidc.entities.OidcConfig;
import com.kbalazsworks.stackjudge.oidc.services.OidcHttpClientService;
import com.kbalazsworks.stackjudge.oidc.services.OidcService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class OidcServiceFactory
{
    private final OidcHttpClientService oidcHttpClient;

    private static final String DISCOVERY_ENDPOINT = "/.well-known/openid-configuration";

    @SneakyThrows
    public OidcService create(String host)
    {
        OidcConfig oidcConfig = oidcHttpClient.getWithMap(host + DISCOVERY_ENDPOINT, OidcConfig.class);

        return new OidcService(oidcConfig, oidcHttpClient);
    }
}
