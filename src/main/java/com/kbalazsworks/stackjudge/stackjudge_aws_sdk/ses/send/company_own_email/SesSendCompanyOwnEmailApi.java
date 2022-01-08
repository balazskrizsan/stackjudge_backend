package com.kbalazsworks.stackjudge.stackjudge_aws_sdk.ses.send.company_own_email;

import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.services.StackJudgeAwsSdkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SesSendCompanyOwnEmailApi
{
    private static final String API_URI = "/ses/send/company-own-email";

    private final StackJudgeAwsSdkService stackJudgeAwsSdkService;

    public void execute(CompanyOwnEmail companyOwnEmail)
    {
        stackJudgeAwsSdkService.post(companyOwnEmail, API_URI);
    }
}
