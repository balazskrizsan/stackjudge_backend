package com.kbalazsworks.stackjudge.domain.common_module.services;

import com.kbalazsworks.stackjudge.api.controllers.company_controller.CompanyConfig;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlService
{
    private final ApplicationProperties applicationProperties;

    public String generateCompanyOwnUrl(@NonNull String secret, long companyId)
    {
        return applicationProperties.getSiteFrontendHost()
            + CompanyConfig.CONTROLLER_URI
            + "/" + companyId
            + CompanyConfig.GET_OWN_COMPLETE_PATH
            + "/code/"
            + secret;
    }
}
