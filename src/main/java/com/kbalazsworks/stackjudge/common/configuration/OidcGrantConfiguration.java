package com.kbalazsworks.stackjudge.common.configuration;

import com.kbalazsworks.simple_oidc.entities.grant_type.ClientCredentials;
import com.kbalazsworks.simple_oidc.entities.grant_type.TokenExchange;
import com.kbalazsworks.simple_oidc.services.IGrantStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.kbalazsworks.stackjudge.common.enums.OidcGrantNamesEnum.*;

@Configuration
@RequiredArgsConstructor
public class OidcGrantConfiguration
{
    private final IGrantStoreService grantStoreService;

    public void init()
    {
        grantStoreService.addGrant(NOTIFICATION__SEND_PUSH.getValue(), new ClientCredentials(
            "sj.notification",
            "sj.notification.client.secret",
            List.of("sj sj.notification sj.notification.send_push")
        ));

        grantStoreService.addGrant(XC__SJ__AWS.getValue(), new ClientCredentials(
            "sj.aws",
            "m2m.client.secret",
            List.of(
                "sj_be",
                "xc/sj_be.aws.ec2",
                "xc/sj_be.aws.ec2.upload_company_logo",
                "xc/sj_be.aws.ec2.upload_company_map"
            )
        ));

        grantStoreService.addGrant(SJ__AWS__SES.getValue(), new ClientCredentials(
            "sj.aws",
            "m2m.client.secret",
            List.of("sj", "sj.aws", "sj.aws.ses", "sj.aws.ses.send_mail")
        ));

        grantStoreService.addGrant(SJ__IDS__API.getValue(), new ClientCredentials(
            "sj.ids.api",
            "sj.ids.api",
            List.of("sj", "sj.ids", "sj.ids.api", "IdentityServerApi")
        ));

        grantStoreService.addGrant(SJ__AWS.getValue(), new TokenExchange(
            "sj.exchange",
            "sj.exchange",
            List.of(
                "sj_be",
                "xc/sj_be.aws.ec2",
                "xc/sj_be.aws.ec2.upload_company_logo",
                "xc/sj_be.aws.ec2.upload_company_map"
            )
        ));

        grantStoreService.protectStore();
    }
}
