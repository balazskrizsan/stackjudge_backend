package com.kbalazsworks.stackjudge.stackjudge_aws_sdk.ses.send_company_own_email;

import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.services.AwsOpenSdkService;
import com.kbalazsworks.stackjudge_aws_sdk.common.interfaces.IOpenSdkPostable;
import com.kbalazsworks.stackjudge_aws_sdk.schema_interfaces.ISesSendCompanyownemail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SesSendCompanyOwnEmailApiService implements ISesSendCompanyownemail
{
    private final AwsOpenSdkService awsOpenSdkService;

    @Override
    public void execute(IOpenSdkPostable postCompanyOwnEmailRequest)
    {
        awsOpenSdkService.post(postCompanyOwnEmailRequest, getApiUri());
    }
}
