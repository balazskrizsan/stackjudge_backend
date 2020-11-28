package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.Company companyTable
        = com.kbalazsworks.stackjudge.db.tables.Company.COMPANY;

    public void delete(long companyId)
    {
        createQueryBuilder()
            .deleteFrom(companyTable)
            .where(companyTable.ID.eq(companyId))
            .execute();
    }

    public Long create(Company company)
    {
        return createQueryBuilder()
            .insertInto(
                companyTable,
                companyTable.NAME,
                companyTable.COMPANY_SIZE_ID,
                companyTable.IT_SIZE_ID,
                companyTable.CREATED_AT,
                companyTable.CREATED_BY
            )
            .values(
                company.name(),
                company.companySizeId(),
                company.itSizeId(),
                company.createdAt(),
                company.createdBy()
            )
            .returningResult(companyTable.ID)
            .fetchOne()
            .getValue(companyTable.ID);
    }

    public List<Company> search(long seekId, int limit)
    {

        return createQueryBuilder()
            .selectFrom(companyTable)
            .orderBy(companyTable.ID.asc())
            .seek(seekId)
            .limit(limit)
            .fetch()
            .into(Company.class);
    }

    public Company get(long companyId) throws RepositoryNotFoundException
    {
        Company company = createQueryBuilder()
            .selectFrom(companyTable)
            .where(companyTable.ID.eq(companyId))
            .fetchOneInto(Company.class);

        if (company == null)
        {
            throw new RepositoryNotFoundException();
        }

        return company;
    }

    public long countRecords()
    {
        return createQueryBuilder()
            .selectCount()
            .from(companyTable)
            .fetchOneInto(Long.class);
    }

    public long countRecordsBeforeId(long seekId)
    {
        return createQueryBuilder()
            .selectCount()
            .from(companyTable)
            .where(companyTable.ID.lessThan(seekId))
            .fetchOneInto(Long.class);
    }
}
