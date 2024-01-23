package com.kbalazsworks.stackjudge.common.configuration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kbalazsworks.simple_oidc.DiConfigModule;
import com.kbalazsworks.simple_oidc.entities.AccessTokenRawResponse;
import com.kbalazsworks.simple_oidc.entities.grant_type.ClientCredentials;
import com.kbalazsworks.simple_oidc.entities.grant_type.TokenExchange;
import com.kbalazsworks.simple_oidc.exceptions.OidcException;
import com.kbalazsworks.simple_oidc.services.HttpClientService;
import com.kbalazsworks.simple_oidc.services.ICommunicationService;
import com.kbalazsworks.simple_oidc.services.IGrantStoreService;
import com.kbalazsworks.simple_oidc.services.ISmartTokenStoreService;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.kbalazsworks.stackjudge.common.enums.OidcGrantNamesEnum.*;

@Configuration
@RequiredArgsConstructor
public class OidcConfiguration
{
    private final ApplicationProperties applicationProperties;
    private       Injector              injector;

    @PostConstruct
    public void postConstruct() throws OidcException
    {
        HttpClientService.host = applicationProperties.getSjIdsFullHost();
        injector               = Guice.createInjector(new DiConfigModule());

        IGrantStoreService grantStoreService = injector.getInstance(IGrantStoreService.class);
        setupStore(grantStoreService);

        ICommunicationService   communicationService   = getCommunicationService();
        ISmartTokenStoreService smartTokenStoreService = getSmartTokenStoreService();

        AccessTokenRawResponse xcSjAws = communicationService.callTokenEndpoint(XC__SJ__AWS.getValue());
        smartTokenStoreService.AddKey(XC__SJ__AWS.getValue(), xcSjAws.getAccessToken());
    }

    @Bean
    public ICommunicationService getCommunicationService()
    {
        return injector.getInstance(ICommunicationService.class);
    }

    @Bean
    public IGrantStoreService getGrantStoreService()
    {
        return injector.getInstance(IGrantStoreService.class);
    }

    @Bean
    public ISmartTokenStoreService getSmartTokenStoreService()
    {
        return injector.getInstance(ISmartTokenStoreService.class);
    }

    public void setupStore(IGrantStoreService grantStoreService)
    {
        grantStoreService.addGrant(NOTIFICATION__SEND_PUSH.getValue(), new ClientCredentials(
            "sj.notification",
            "sj.notification.client.secret",
            List.of("sj sj.notification sj.notification.send_push")
        ));

        grantStoreService.addGrant(XC__SJ__AWS.getValue(), new ClientCredentials(
            "sj.aws",
            "sj.aws",
            List.of(
                "xc/sj.aws"
            )
        ));

        grantStoreService.addGrant(SJ__AWS.getValue(), new TokenExchange(
            "sj.exchange",
            "sj.exchange",
            List.of("sj.aws")
        ));

        grantStoreService.addGrant(SJ__AWS__SES.getValue(), new ClientCredentials(
            "sj.aws",
            "m2m.client.secret",
            List.of("sj", "sj.aws", "sj.aws.ses", "sj.aws.ses.send_mail")
        ));

        grantStoreService.addGrant(SJ__IDS__API.getValue(), new ClientCredentials(
            "sj.ids.api",
            "sj.ids.api",
            List.of("sj", "sj.ids.api", "IdentityServerApi")
        ));

        grantStoreService.protectStore();
    }
}
