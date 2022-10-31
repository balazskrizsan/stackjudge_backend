package com.kbalazsworks.stackjudge.domain.common_module.repositories;

import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwner;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CompanyOwnersRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.CompanyOwner companyOwnerTable
        = com.kbalazsworks.stackjudge.db.tables.CompanyOwner.COMPANY_OWNER;

    public void create(CompanyOwner companyOwner)
    {
        getQueryBuilder()
            .insertInto(
                companyOwnerTable,
                companyOwnerTable.COMPANY_ID,
                companyOwnerTable.USER_IDS_USER_ID,
                companyOwnerTable.CREATED_AT
            )
            .values(
                companyOwner.companyId(),
                companyOwner.userId(),
                companyOwner.createdAt()
            )
            .execute();
    }

    public boolean isUserOwnerOnCompany(String idsUserId, long companyId)
    {
        // @formatter:off
        DSLContext qB = getQueryBuilder();

        return qB.fetchExists(
            qB
                .selectFrom(companyOwnerTable)
                .where(
                    companyOwnerTable.USER_IDS_USER_ID.eq(idsUserId)
                        .and(companyOwnerTable.COMPANY_ID.eq(companyId))
                )
        );
        // @formatter:on
    }

    public Map<Long, List<String>> searchByCompanyId(List<Long> companyId)
    {
        return getQueryBuilder()
            .selectFrom(companyOwnerTable)
            .where(companyOwnerTable.COMPANY_ID.in(companyId))
            .fetchGroups(companyOwnerTable.COMPANY_ID, companyOwnerTable.USER_IDS_USER_ID);
    }
}
