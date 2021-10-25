package com.kbalazsworks.stackjudge.domain.aws_module.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.kbalazsworks.stackjudge.domain.aws_module.exceptions.EmailHttpException;
import com.kbalazsworks.stackjudge.domain.aws_module.factories.AmazonSimpleEmailServiceFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class SesService
{
    private final AmazonSimpleEmailServiceFactory amazonSimpleEmailServiceFactory;

    private final static String FROM_EMAIL = "krizsan.balazs@gmail.com";

    public void sendMail(@NonNull String to, @NonNull String subject, @NonNull String html, @NonNull String text)
    {
        try
        {
            AmazonSimpleEmailService client = amazonSimpleEmailServiceFactory.create();

            SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message()
                    .withBody(
                        new Body()
                            .withHtml(new Content().withCharset("UTF-8").withData(html))
                            .withText(new Content().withCharset("UTF-8").withData(text))
                    )
                    .withSubject(new Content().withCharset("UTF-8").withData(subject))
                )
                .withSource(FROM_EMAIL);

            client.sendEmail(request);
        }
        catch (Exception e)
        {
            log.error("Email send error.", e);

            // @todo3: test
            throw new EmailHttpException("E-mail send error.");
        }
    }
}
