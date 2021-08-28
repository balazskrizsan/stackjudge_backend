package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.enums.paginator.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.CompanyException;
import com.kbalazsworks.stackjudge.domain.exceptions.ExceptionResponseInfo;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import lombok.NonNull;
import org.jooq.*;
import org.springframework.http.HttpStatus;
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
        getQueryBuilder()
            .deleteFrom(companyTable)
            .where(companyTable.ID.eq(companyId))
            .execute();
    }

    public Long create(@NonNull Company company)
    {
        return getQueryBuilder()
            .insertInto(
                companyTable,
                companyTable.NAME,
                companyTable.DOMAIN,
                companyTable.COMPANY_SIZE_ID,
                companyTable.IT_SIZE_ID,
                companyTable.LOGO_PATH,
                companyTable.CREATED_AT,
                companyTable.CREATED_BY
            )
            .values(
                company.name(),
                company.domain(),
                company.companySizeId(),
                company.itSizeId(),
                company.logoPath(),
                company.createdAt(),
                company.createdBy()
            )
            .returningResult(companyTable.ID)
            .fetchOne()
            .getValue(companyTable.ID);
    }

    public List<Company> search(long seekId, int limit)
    {
        return getQueryBuilder()
            .selectFrom(companyTable)
            .where(companyTable.ID.greaterOrEqual(seekId))
            .orderBy(companyTable.ID)
            .limit(limit)
            .fetchInto(Company.class);
    }

    public List<Company> search(NavigationEnum navigation, int limit)
    {
        return getQueryBuilder()
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
                subQuery = getQueryBuilder()
                    .select(companyTable.ID)
                    .from(companyTable)
                    .orderBy(companyTable.ID)
                    .limit(limit + 1)
                    .asTable(innerCompanyAlias);
                order    = innerCompany.ID.desc();
            }
            case LAST_MINUS_1 -> {
                subQuery = getQueryBuilder()
                    .select(companyTable.ID)
                    .from(companyTable)
                    .orderBy(companyTable.ID.desc())
                    .limit(limit * 2)
                    .asTable(innerCompanyAlias);
                order    = innerCompany.ID.asc();
            }
            case LAST -> {
                subQuery = getQueryBuilder()
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
            getQueryBuilder()
                .select(innerCompany.ID)
                .from(subQuery)
                .orderBy(order)
                .limit(1)
        );
    }

    public List<Company> search(long seekId, @NonNull NavigationEnum navigation, int limit)
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

        return getQueryBuilder()
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
                getQueryBuilder()
                    .select(innerCompany.ID)
                    .from(
                        getQueryBuilder()
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
                getQueryBuilder()
                    .select(innerCompany.ID)
                    .from(
                        getQueryBuilder()
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
        Company company = getQueryBuilder()
            .selectFrom(companyTable)
            .where(companyTable.ID.eq(companyId))
            .fetchOneInto(Company.class);

        if (company == null)
        {
            throw new RepositoryNotFoundException(ExceptionResponseInfo.CompanyNotFoundMsg)
                .withErrorCode(ExceptionResponseInfo.CompanyNotFoundErrorCode)
                .withStatusCode(HttpStatus.NOT_FOUND);
        }

        return company;
    }

    public long countRecords()
    {
        return getQueryBuilder()
            .selectCount()
            .from(companyTable)
            .fetchOneInto(Long.class);
    }

    public long countRecordsBeforeId(long id)
    {
        return getQueryBuilder()
            .selectCount()
            .from(companyTable)
            .where(companyTable.ID.lessThan(id))
            .fetchOneInto(Long.class);
    }

    public void updateLogoPath(long companyId, String logoPath)
    {
        getQueryBuilder()
            .update(companyTable)
            .set(companyTable.LOGO_PATH, logoPath)
            .where(companyTable.ID.eq(companyId))
            .execute();
    }
}
