package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.aspect_enums.RedisCacheRepositorieEnum;
import com.kbalazsworks.stackjudge.domain.aspects.RedisCacheByCompanyIdList;
import com.kbalazsworks.stackjudge.domain.entities.CompanyOwner;
import com.kbalazsworks.stackjudge.domain.entities.CompanyOwners;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                companyOwnerTable.USER_ID,
                companyOwnerTable.CREATED_AT
            )
            .values(
                companyOwner.companyId(),
                companyOwner.userId(),
                companyOwner.createdAt()
            )
            .execute();
    }

    public boolean isUserOwnerOnCompany(long userId, long companyId)
    {
        DSLContext qB = getQueryBuilder();
        return qB.fetchExists(
            qB
                .selectFrom(companyOwnerTable)
                .where(
                    companyOwnerTable.USER_ID.eq(userId)
                        .and(companyOwnerTable.COMPANY_ID.eq(companyId))
                )
        );
    }

    // @todo: CompanyOwners conversion should be in the service
    @RedisCacheByCompanyIdList(repository = RedisCacheRepositorieEnum.COMPANY_OWNER)
    public Map<Long, CompanyOwners> searchByCompanyId(List<Long> companyId)
    {
        return getQueryBuilder()
            .selectFrom(companyOwnerTable)
            .where(companyOwnerTable.COMPANY_ID.in(companyId))
            .fetchGroups(companyOwnerTable.COMPANY_ID, companyOwnerTable.USER_ID)
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, r -> new CompanyOwners(r.getKey(), r.getValue())));
    }
}
