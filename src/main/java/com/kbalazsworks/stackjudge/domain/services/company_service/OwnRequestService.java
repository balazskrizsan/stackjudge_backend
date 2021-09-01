package com.kbalazsworks.stackjudge.domain.services.company_service;

import com.kbalazsworks.stackjudge.common.services.SecureRandomService;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.entities.CompanyOwnRequest;
import com.kbalazsworks.stackjudge.domain.entities.TypedPersistenceLog;
import com.kbalazsworks.stackjudge.domain.entities.persistence_log.DataOwnRequestSent;
import com.kbalazsworks.stackjudge.domain.enums.PersistenceLogTypeEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.PebbleException;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyOwnRequestRepository;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import com.kbalazsworks.stackjudge.domain.services.JooqService;
import com.kbalazsworks.stackjudge.domain.services.PersistenceLogService;
import com.kbalazsworks.stackjudge.domain.services.UrlService;
import com.kbalazsworks.stackjudge.domain.services.aws_services.SendCompanyOwnEmailService;
import com.kbalazsworks.stackjudge.domain.value_objects.company_service.OwnRequest;
import com.kbalazsworks.stackjudge.state.entities.State;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.Configuration;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnRequestService
{
    private final PersistenceLogService       persistenceLogService;
    private final SecureRandomService         secureRandomService;
    private final SendCompanyOwnEmailService  sendCompanyOwnEmailService;
    private final CompanyService              companyService;
    private final UrlService                  urlService;
    private final JooqService                 jooqService;
    private final CompanyOwnRequestRepository companyOwnRequestRepository;

    // @todo: test
    public void own(OwnRequest ownRequest, State state)
    {
        boolean success = jooqService.getDbContext().transactionResult(
            (Configuration config) ->
            {

                String  secret             = secureRandomService.getUrlEncoded(32);
                long    requesterUserId    = state.currentUser().getId();
                long    requestedCompanyId = ownRequest.companyId();
                Company company            = companyService.get(requestedCompanyId);

                companyOwnRequestRepository.create(new CompanyOwnRequest(
                    requesterUserId,
                    requestedCompanyId,
                    secret,
                    state.now()
                ));

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
                        urlService.generateCompanyOwnUrl(secret)
                    );
                }
                catch (PebbleException e)
                {
                    return false;
                }

                return true;
            });

        // @todo: add error handling
    }

    private String generateEmailAddress(@NonNull Company company, @NonNull String emailPart)
    {
        return emailPart + "@" + company.domain().toLowerCase().replaceAll("^https?://", "");
    }
}
