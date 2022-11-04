package com.kbalazsworks.stackjudge.integration.domain.company_module.services;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwnRequest;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.OwnRequest;
import com.kbalazsworks.stackjudge.domain.email_module.services.CompanyOwnEmailService;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.entities.DataOwnRequestSent;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.enums.PersistenceLogTypeEnum;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.services.PersistenceLogService;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.IdsUserFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.mocking.setup_mock.PersistenceLogServiceMocker;
import com.kbalazsworks.stackjudge.mocking.setup_mock.SecureRandomServiceMocker;
import com.kbalazsworks.stackjudge.mocking.setup_mock.UrlServiceMocker;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;

import static com.kbalazsworks.stackjudge.MockFactory.TEST_STATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class OwnRequestService_OwnTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_1_company.sql",
                    "classpath:test/sqls/preset_add_1_user.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    @SneakyThrows
    public void validOwnRequest_willSavedAndLogged()
    {
        // Arrange
        OwnRequest testedOwnRequest = new OwnRequest(CompanyFakeBuilder.defaultId1, "admin");

        int    mockedRandomLength   = 32;
        String mockedRandomResponse = "mocked_random_secret";
        String mockedString         = "mocked_random_secret";
        String mockerReturnUrl      = "mocked_callback_url";

        String expectedToAddress = "admin@test-company.com";
        String expectedName      = new IdsUserFakeBuilder().userName();
        String expectedOwnUrl    = "mocked_callback_url";
        long   mockedCompanyId   = CompanyFakeBuilder.defaultId1;

        CompanyOwnRequest expectedCompanyOwnRequest = new CompanyOwnRequest(
            UserFakeBuilder.defaultId1,
            CompanyFakeBuilder.defaultId1,
            mockedRandomResponse,
            TEST_STATE.now()
        );

        Long                   expectedLogId                    = null;
        PersistenceLogTypeEnum expectedLogPersistenceLogService = PersistenceLogTypeEnum.OWN_REQUEST_SENT;
        DataOwnRequestSent expectedLogData = new DataOwnRequestSent(
            UserFakeBuilder.defaultId1,
            CompanyFakeBuilder.defaultId1
        );
        LocalDateTime expectedLogNow = TEST_STATE.now();

        CompanyOwnEmailService companyOwnEmailServiceMock = MockCreator.getSendCompanyOwnEmailService();
        PersistenceLogService  persistenceLogServiceMock  = MockCreator.getPersistenceLogService();

        // Act
        serviceFactory.getOwnRequestService(
            persistenceLogServiceMock,
            SecureRandomServiceMocker.getUrlEncoded_returns_string(mockedRandomLength, mockedRandomResponse),
            companyOwnEmailServiceMock,
            null,
            null,
            UrlServiceMocker.generateCompanyOwnUrl_return_url(mockedString, mockedCompanyId, mockerReturnUrl),
            null,
            null,
            null
          )
          .own(testedOwnRequest, TEST_STATE);

        // Assert
        assertAll(
            () -> assertThat(getQueryBuilder().selectFrom(companyOwnRequestTable).fetchOneInto(CompanyOwnRequest.class))
                .isEqualTo(expectedCompanyOwnRequest),
            () -> verify(companyOwnEmailServiceMock, only())
                .send(eq(expectedToAddress), eq(expectedName), eq(expectedOwnUrl)),
            () -> PersistenceLogServiceMocker.create_verifier(
                persistenceLogServiceMock,
                expectedLogId,
                expectedLogPersistenceLogService,
                expectedLogData,
                expectedLogNow
            )
        );
    }
}
