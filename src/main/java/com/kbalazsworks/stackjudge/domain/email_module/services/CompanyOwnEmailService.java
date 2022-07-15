package com.kbalazsworks.stackjudge.domain.email_module.services;

import com.kbalazsworks.stackjudge.domain.common_module.exceptions.PebbleException;
import com.kbalazsworks.stackjudge_aws_sdk.schema_interfaces.ISesSendCompanyOwnEmail;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PostCompanyOwnEmailRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CompanyOwnEmailService
{
    private final ISesSendCompanyOwnEmail sesSendCompanyOwnEmailApiService;

    @SneakyThrows
    public void send(
        @NonNull String toAddress,
        @NonNull String name,
        @NonNull String ownUrl
    ) throws PebbleException
    {
        sesSendCompanyOwnEmailApiService.postAsync(new PostCompanyOwnEmailRequest(
            toAddress,
            name,
            ownUrl
        ));
    }
}
