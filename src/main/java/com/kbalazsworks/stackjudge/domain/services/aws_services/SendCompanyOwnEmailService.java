package com.kbalazsworks.stackjudge.domain.services.aws_services;

import com.kbalazsworks.stackjudge.domain.exceptions.PebbleException;
import com.kbalazsworks.stackjudge.domain.services.PebbleTemplateService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class SendCompanyOwnEmailService
{
    private final SesService            sesService;
    private final PebbleTemplateService pebbleTemplateService;

    private final static String SUBJECT = "";

    // @todo: test
    @SneakyThrows public void sendCompanyOwnEmail(@NonNull String name, @NonNull String ownUrl) throws PebbleException
    {
        Map<String, Object> context = new HashMap<>();
        context.put("name", name);
        context.put("ownUrl", ownUrl);

        String companyOwnHtml = pebbleTemplateService.render("mail/company_own.html", context);
        String companyOwnText = pebbleTemplateService.render("mail/company_own.txt", context);

        sesService.sendMail("krizsan.balazs@gmail.com", SUBJECT, companyOwnHtml, companyOwnText);
    }
}
