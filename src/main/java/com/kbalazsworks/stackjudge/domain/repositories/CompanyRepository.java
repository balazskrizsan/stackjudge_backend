package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.enums.paginator.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.CompanyException;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import org.jooq.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jooq.impl.DSL.field;

@Repository
public class CompanyRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.Company companyTable
        = com.kbalazsworks.stackjudge.db.tables.Company.COMPANY;

    String innerCompanyAlias = "inner_company";
    com.kbalazsworks.stackjudge.db.tables.Company
           innerCompany      = com.kbalazsworks.stackjudge.db.tables.Company.COMPANY.as(innerCompanyAlias);

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
            .where(companyTable.ID.greaterOrEqual(seekId))
            .orderBy(companyTable.ID)
            .limit(limit)
            .fetchInto(Company.class);
    }

    public List<Company> search(NavigationEnum navigation, int limit)
    {
        return createQueryBuilder()
            .selectFrom(companyTable)
            .where(companyTable.ID.greaterOrEqual(getSeekSubQueryForSeekId(limit, navigation)))
            .orderBy(companyTable.ID)
            .limit(limit)
            .fetchInto(Company.class);
    }

    private Field<Long> getSeekSubQueryForSeekId(int limit, NavigationEnum navigation)
    {
        Table<Record1<Long>> subQuery;
        SortField<Long>      order;
        switch (navigation)
        {
            case SECOND -> {
                subQuery = createQueryBuilder()
                    .select(companyTable.ID)
                    .from(companyTable)
                    .orderBy(companyTable.ID)
                    .limit(limit + 1)
                    .asTable(innerCompanyAlias);
                order    = innerCompany.ID.desc();
            }
            case LAST_MINUS_1 -> {
                subQuery = createQueryBuilder()
                    .select(companyTable.ID)
                    .from(companyTable)
                    .orderBy(companyTable.ID.desc())
                    .limit(limit * 2)
                    .asTable(innerCompanyAlias);
                order    = innerCompany.ID.asc();
            }
            case LAST -> {
                subQuery = createQueryBuilder()
                    .select(companyTable.ID)
                    .from(companyTable)
                    .orderBy(companyTable.ID.desc())
                    .limit(limit)
                    .asTable(innerCompanyAlias);
                order    = innerCompany.ID.asc();
            }
            default -> throw new CompanyException(
                "Seek sub query not found with enumId#".concat(navigation.getValue().toString())
            );
        }

        return field(
            createQueryBuilder()
                .select(innerCompany.ID)
                .from(subQuery)
                .orderBy(order)
                .limit(1)
        );
    }

    public List<Company> search(long seekId, NavigationEnum navigation, int limit)
    {
        Condition where = switch (navigation)
            {
                case CURRENT_PLUS_1 -> searchCurrentPlusWhereLogic(seekId, limit);
                case CURRENT_MINUS_1 -> searchCurrentMinusWhereLogic(seekId, limit);
                case CURRENT_PLUS_2 -> searchCurrentPlusWhereLogic(seekId, limit * 2);
                case CURRENT_MINUS_2 -> searchCurrentMinusWhereLogic(seekId, limit * 2);
                default -> throw new CompanyException(
                    "Seek sub query not found with enumId#".concat(navigation.getValue().toString())
                );
            };

        return createQueryBuilder()
            .selectFrom(companyTable)
            .where(where)
            .orderBy(companyTable.ID)
            .limit(limit)
            .fetchInto(Company.class);
    }

    public Condition searchCurrentPlusWhereLogic(long seekId, int limit)
    {
        return companyTable.ID.greaterOrEqual(
            field(
                createQueryBuilder()
                    .select(innerCompany.ID)
                    .from(
                        createQueryBuilder()
                            .select(companyTable.ID)
                            .from(companyTable)
                            .where(companyTable.ID.greaterThan(seekId))
                            .orderBy(companyTable.ID)
                            .limit(limit)
                            .asTable(innerCompanyAlias)
                    )
                    .orderBy(innerCompany.ID.desc())
                    .limit(1)
            )
        );
    }

    private Condition searchCurrentMinusWhereLogic(long seekId, int limit)
    {
        return companyTable.ID.greaterOrEqual(
            field(
                createQueryBuilder()
                    .select(innerCompany.ID)
                    .from(
                        createQueryBuilder()
                            .select(companyTable.ID)
                            .from(companyTable)
                            .where(companyTable.ID.lessThan(seekId))
                            .orderBy(companyTable.ID.desc())
                            .limit(limit)
                            .asTable(innerCompanyAlias)
                    )
                    .orderBy(innerCompany.ID)
                    .limit(1)
            )
        );
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

    public long countRecordsBeforeId(long id)
    {
        return createQueryBuilder()
            .selectCount()
            .from(companyTable)
            .where(companyTable.ID.lessThan(id))
            .fetchOneInto(Long.class);
    }
}
