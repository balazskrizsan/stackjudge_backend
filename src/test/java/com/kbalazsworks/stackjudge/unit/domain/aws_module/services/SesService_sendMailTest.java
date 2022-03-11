package com.kbalazsworks.stackjudge.unit.domain.aws_module.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.verify;

public class SesService_sendMailTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void validParameters_callsSesSendService()
    {
        // Arrange
        String testedTo      = "to@email.com";
        String testedSubject = "subject";
        String testedHtml    = "html";
        String testedText    = "text";

//        SendEmailRequest expectedSendRequest = new SendEmailRequest()
//            .withDestination(new Destination().withToAddresses("to@email.com"))
//            .withMessage(new Message()
//                .withBody(
//                    new Body()
//                        .withHtml(new Content().withCharset("UTF-8").withData("html"))
//                        .withText(new Content().withCharset("UTF-8").withData("text"))
//                )
//                .withSubject(new Content().withCharset("UTF-8").withData("subject"))
//            )
//            .withSource("krizsan.balazs@gmail.com");

        // @todo: restore test
//        AmazonSimpleEmailService amazonSimpleEmailServiceMock = MockCreator.getAmazonSimpleEmailService();
//
//         Act
//        serviceFactory
//            .getSesService(AmazonSimpleEmailServiceFactoryMocker.create_returns_mock(amazonSimpleEmailServiceMock))
//            .sendMail(testedTo, testedSubject, testedHtml, testedText);
//
//         Assert
//        verify(amazonSimpleEmailServiceMock).sendEmail(expectedSendRequest);
    }
}
