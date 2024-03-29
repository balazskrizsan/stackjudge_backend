package com.kbalazsworks.stackjudge.domain.company_module.services;

import com.kbalazsworks.stackjudge.common.services.SecureRandomService;
import com.kbalazsworks.stackjudge.db_migrations.DbConstants;
import com.kbalazsworks.stackjudge.domain.common_module.repositories.CompanyOwnRequestRepository;
import com.kbalazsworks.stackjudge.domain.common_module.services.HttpExceptionService;
import com.kbalazsworks.stackjudge.domain.common_module.services.JooqService;
import com.kbalazsworks.stackjudge.domain.common_module.services.UrlService;
import com.kbalazsworks.stackjudge.domain.company_module.entities.Company;
import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwnRequest;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.OwnRequest;
import com.kbalazsworks.stackjudge.domain.email_module.services.CompanyOwnEmailService;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.entities.DataOwnRequestSent;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.entities.TypedPersistenceLog;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.enums.PersistenceLogTypeEnum;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.services.PersistenceLogService;
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
    private final CompanyOwnEmailService      companyOwnEmailService;
    private final CompanyOwnersService        companyOwnersService;
    private final CompanyService              companyService;
    private final UrlService                  urlService;
    private final HttpExceptionService        httpExceptionService;
    private final JooqService                 jooqService;
    private final CompanyOwnRequestRepository companyOwnRequestRepository;

    public void own(@NonNull OwnRequest ownRequest, @NonNull State state)
    {
        // @todo2: test condition
        if (companyOwnersService.isUserOwnerOnCompany(state.currentIdsUser().getId(), ownRequest.companyId()))
        {
            httpExceptionService.throwCompanyAlreadyOwnedByTheUser();
        }

        boolean success = jooqService.getDbContext().transactionResult(
            (Configuration config) -> transactionalOwn(ownRequest, state)
        );

        // @todo3: test
        if (!success)
        {
            httpExceptionService.throwCompanyOwnRequestFailed();
        }
    }

    private boolean transactionalOwn(@NonNull OwnRequest ownRequest, @NonNull State state) throws Exception
    {
        String  secret             = secureRandomService.getUrlEncoded(32);
        String  requesterUserId    = state.currentIdsUser().getId();
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

        // @todo: check error
        companyOwnEmailService.send(
            generateEmailAddress(company, ownRequest.emailPart()),
            state.currentIdsUser().getUserName(),
            urlService.generateCompanyOwnUrl(secret, ownRequest.companyId())
        );

        return true;
    }

    private @NonNull String generateEmailAddress(@NonNull Company company, @NonNull String emailPart)
    {
        return emailPart + "@" + company.getDomain().toLowerCase().replaceAll("^https?://", "");
    }

    public CompanyOwnRequest getAndDeleteByCode(@NonNull String code)
    {
        CompanyOwnRequest companyOwnRequest = companyOwnRequestRepository.getByCode(code);
        companyOwnRequestRepository.deleteByCode(code);

        return companyOwnRequest;
    }
}
