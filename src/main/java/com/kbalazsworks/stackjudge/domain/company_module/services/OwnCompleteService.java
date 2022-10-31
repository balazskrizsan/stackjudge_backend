package com.kbalazsworks.stackjudge.domain.company_module.services;

import com.kbalazsworks.stackjudge.api.requests.OwnComplete;
import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwnRequest;
import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwner;
import com.kbalazsworks.stackjudge.domain.common_module.services.HttpExceptionService;
import com.kbalazsworks.stackjudge.state.entities.State;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class OwnCompleteService
{
    private final OwnRequestService    ownRequestService;
    private final CompanyOwnersService companyOwnersService;
    private final HttpExceptionService httpExceptionService;

    // @todo: test
    public void complete(@NonNull OwnComplete ownComplete, @NonNull State state)
    {
        try
        {
            CompanyOwnRequest companyOwnRequest = ownRequestService.getAndDeleteByCode(ownComplete.code());

            String idsUserId    = companyOwnRequest.requesterIdsUserId();
            long companyId = companyOwnRequest.requestedCompanyId();

            if (companyOwnersService.isUserOwnerOnCompany(idsUserId, companyId))
            {
                httpExceptionService.throwCompanyAlreadyOwnedByTheUser();
            }

            try
            {
                companyOwnersService.own(new CompanyOwner(companyId, idsUserId, state.now()));
            }
            catch (Exception e)
            {
                httpExceptionService.throwCompanyOwnCompleteRequestFailed();
            }
        }
        catch (Exception e)
        {
            httpExceptionService.throwCompanyOwnCompleteRequestFailed();
        }
    }
}
