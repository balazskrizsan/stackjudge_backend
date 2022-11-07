package com.kbalazsworks.stackjudge.common.configuration;

import com.kbalazsworks.simple_oidc.entities.grant_type.ClientCredentials;
import com.kbalazsworks.simple_oidc.exceptions.GrantStoreException;
import com.kbalazsworks.simple_oidc.services.GrantStoreService;
import com.kbalazsworks.simple_oidc.services.IOidcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.kbalazsworks.stackjudge.common.enums.OidcGrantNamesEnum.*;

@Component
@RequiredArgsConstructor
public class OidcGrantConfiguration
{
    private final IOidcService oidcService;

    public void init() throws GrantStoreException
    {
        GrantStoreService grantStoreService = oidcService.getGrantStoreService();

        grantStoreService.addGrant(NOTIFICATION__SEND_PUSH.getValue(), new ClientCredentials(
            "sj.notification",
            "sj.notification.client.secret",
            List.of("sj sj.notification sj.notification.send_push")
        ));

        grantStoreService.addGrant(SJ__AWS__EC2.getValue(), new ClientCredentials(
            "sj.aws",
            "m2m.client.secret",
            List.of("sj", "sj.aws", "sj.aws.ec2", "sj.aws.ec2.upload_company_logo", "sj.aws.ec2.upload_company_map")
        ));

        grantStoreService.addGrant(SJ__AWS__SES.getValue(), new ClientCredentials(
            "sj.aws",
            "m2m.client.secret",
            List.of("sj", "sj.aws", "sj.aws.ses", "sj.aws.ses.send_mail")
        ));

        grantStoreService.addGrant(SJ__IDS__API.getValue(), new ClientCredentials(
            "sj.ids.api",
            "sj.ids.api.secret",
            List.of("sj", "sj.ids", "sj.ids.api", "IdentityServerApi")
        ));

        grantStoreService.protectStore();
    }
}
