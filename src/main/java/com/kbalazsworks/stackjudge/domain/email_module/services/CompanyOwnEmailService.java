package com.kbalazsworks.stackjudge.domain.email_module.services;

import com.kbalazsworks.stackjudge.domain.aws_module.services.SesService;
import com.kbalazsworks.stackjudge.domain.common_module.exceptions.PebbleException;
import com.kbalazsworks.stackjudge.domain.common_module.services.PebbleTemplateService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class CompanyOwnEmailService
{
    private final SesService            sesService;
    private final PebbleTemplateService pebbleTemplateService;

    private final static String SUBJECT = "StackJudge - Company Own Request";

    public void send(
        @NonNull String toAddress,
        @NonNull String name,
        @NonNull String ownUrl
    ) throws PebbleException
    {
        Map<String, Object> context = new HashMap<>()
        {{
            put("name", name);
            put("ownUrl", ownUrl);
        }};

        String companyOwnHtml = pebbleTemplateService.render("mail/company_own.html", context);
        String companyOwnText = pebbleTemplateService.render("mail/company_own.txt", context);

        sesService.sendMail(toAddress, SUBJECT, companyOwnHtml, companyOwnText);
    }
}
