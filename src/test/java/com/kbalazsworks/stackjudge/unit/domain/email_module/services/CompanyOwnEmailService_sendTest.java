package com.kbalazsworks.stackjudge.unit.domain.email_module.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.common_module.services.PebbleTemplateService;
import com.kbalazsworks.stackjudge.domain.aws_module.services.SesService;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CompanyOwnEmailService_sendTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    @SneakyThrows
    public void validEmailParameters_callsSesSendMail()
    {
        // Arrange
        String testedToAddress = "to@email.com";
        String testedName      = "test name";
        String testedUwnUrl    = "https://test.com/uri";

        String mockedWhenTemplate1 = "mail/company_own.html";
        String mockedThanString1   = "html";
        String mockedWhenTemplate2 = "mail/company_own.txt";
        String mockedThanString2   = "text";
        Map<String, Object> mockerContext = Map.of(
            "name", testedName,
            "ownUrl", testedUwnUrl
        );

        String expectedToAddress = "to@email.com";
        String expectedHtml      = "html";
        String expectedText      = "text";
        String expectedSubject   = "StackJudge - Company Own Request";

        PebbleTemplateService mock = MockCreator.getPebbleTemplateService();
        when(mock.render(mockedWhenTemplate1, mockerContext)).thenReturn(mockedThanString1);
        when(mock.render(mockedWhenTemplate2, mockerContext)).thenReturn(mockedThanString2);

        SesService sesServiceMock = MockCreator.getSesService();

        // Act
        serviceFactory
            .getCompanyOwnEmailService(sesServiceMock, mock)
            .send(testedToAddress, testedName, testedUwnUrl);

        // Assert
        verify(sesServiceMock).sendMail(expectedToAddress, expectedSubject, expectedHtml, expectedText);
    }
}
