package com.kbalazsworks.stackjudge.domain.services.company;

import com.kbalazsworks.stackjudge.common.services.SecureRandomService;
import com.kbalazsworks.stackjudge.db_migrations.DbConstants;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.entities.CompanyOwnRequest;
import com.kbalazsworks.stackjudge.domain.entities.TypedPersistenceLog;
import com.kbalazsworks.stackjudge.domain.entities.persistence_log.DataOwnRequestSent;
import com.kbalazsworks.stackjudge.domain.enums.PersistenceLogTypeEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.PebbleException;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyOwnRequestRepository;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import com.kbalazsworks.stackjudge.domain.services.HttpExceptionService;
import com.kbalazsworks.stackjudge.domain.services.JooqService;
import com.kbalazsworks.stackjudge.domain.services.PersistenceLogService;
import com.kbalazsworks.stackjudge.domain.services.UrlService;
import com.kbalazsworks.stackjudge.domain.services.aws_services.SendCompanyOwnEmailService;
import com.kbalazsworks.stackjudge.domain.value_objects.company_service.OwnRequest;
import com.kbalazsworks.stackjudge.state.entities.State;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jooq.Configuration;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class OwnRequestService
{
    private final PersistenceLogService       persistenceLogService;
    private final SecureRandomService         secureRandomService;
    private final SendCompanyOwnEmailService  sendCompanyOwnEmailService;
    private final CompanyService              companyService;
    private final UrlService                  urlService;
    private final HttpExceptionService        httpExceptionService;
    private final JooqService                 jooqService;
    private final CompanyOwnRequestRepository companyOwnRequestRepository;

    public void own(@NonNull OwnRequest ownRequest, @NonNull State state)
    {
        boolean success = jooqService.getDbContext().transactionResult(
            (Configuration config) -> transactionalOwn(ownRequest, state)
        );

        // @todo3: test
        if (!success)
        {
            httpExceptionService.throwCompanyOwnRequestFailed();
        }
    }

    private boolean transactionalOwn(@NonNull OwnRequest ownRequest, @NonNull State state)
    {
        String  secret             = secureRandomService.getUrlEncoded(32);
        long    requesterUserId    = state.currentUser().getId();
        long    requestedCompanyId = ownRequest.companyId();
        Company company            = companyService.get(requestedCompanyId);

        try
        {
            companyOwnRequestRepository.create(new CompanyOwnRequest(
                requesterUserId,
                requestedCompanyId,
                secret,
                state.now()
            ));
        }
        // @todo3: test
        catch (Exception e)
        {
            if (e.getCause().toString().contains(DbConstants.COMPANY_OWN_REQUEST_PK))
            {
                httpExceptionService.throwCompanyOwnRequestAlreadySent();
            }

            httpExceptionService.throwCompanyOwnRequestFailed();
        }

        persistenceLogService.create(new TypedPersistenceLog<>(
            null,
            PersistenceLogTypeEnum.OWN_REQUEST_SENT,
            new DataOwnRequestSent(requesterUserId, requestedCompanyId),
            state.now()
        ));

        try
        {
            sendCompanyOwnEmailService.sendCompanyOwnEmail(
                generateEmailAddress(company, ownRequest.emailPart()),
                state.currentUser().getUsername(),
                urlService.generateCompanyOwnUrl(secret, ownRequest.companyId())
            );
        }
        // @todo3: test
        catch (PebbleException e)
        {
            log.error("Pebble email generation error: " + e.getMessage());

            return false;
        }

        return true;
    }

    private @NonNull String generateEmailAddress(@NonNull Company company, @NonNull String emailPart)
    {
        return emailPart + "@" + company.domain().toLowerCase().replaceAll("^https?://", "");
    }
}
