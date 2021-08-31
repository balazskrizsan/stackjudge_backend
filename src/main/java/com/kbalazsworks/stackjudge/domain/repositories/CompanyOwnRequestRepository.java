package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.entities.CompanyOwnRequest;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class CompanyOwnRequestRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.CompanyOwnRequest companyOwnRequestTable
        = com.kbalazsworks.stackjudge.db.tables.CompanyOwnRequest.COMPANY_OWN_REQUEST;

    public void create(@NonNull CompanyOwnRequest companyOwnRequest)
    {
        getQueryBuilder()
            .insertInto(
                companyOwnRequestTable,
                companyOwnRequestTable.REQUESTER_USER_ID,
                companyOwnRequestTable.REQUESTED_COMPANY_ID,
                companyOwnRequestTable.SECRET,
                companyOwnRequestTable.CREATED_AT
            )
            .values(
                companyOwnRequest.requesterUserId(),
                companyOwnRequest.requestedCompanyId(),
                companyOwnRequest.secret(),
                companyOwnRequest.createdAt()
            )
            .execute();
    }
}
