package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ses.send_company_own_email;

import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services.AwsOpenSdkService;
import com.kbalazsworks.stackjudge_aws_sdk.common.exceptions.ResponseException;
import com.kbalazsworks.stackjudge_aws_sdk.common.interfaces.IOpenSdkPostable;
import com.kbalazsworks.stackjudge_aws_sdk.schema_interfaces.ISesSendCompanyOwnEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SesSendCompanyOwnEmailApiService implements ISesSendCompanyOwnEmail
{
    private final AwsOpenSdkService awsOpenSdkService;

    @Override
    public void post(IOpenSdkPostable postCompanyOwnEmailRequest) throws ResponseException
    {
        awsOpenSdkService.post(postCompanyOwnEmailRequest, getApiUri());
    }

    @Async
    @Override
    public void postAsync(IOpenSdkPostable postCompanyOwnEmailRequest) throws ResponseException
    {
        awsOpenSdkService.post(postCompanyOwnEmailRequest, getApiUri());
    }
}
