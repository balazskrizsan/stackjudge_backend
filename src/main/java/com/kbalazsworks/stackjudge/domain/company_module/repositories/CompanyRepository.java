package com.kbalazsworks.stackjudge.domain.company_module.repositories;

import com.kbalazsworks.stackjudge.domain.common_module.exceptions.ExceptionResponseInfo;
import com.kbalazsworks.stackjudge.domain.common_module.exceptions.RepositoryNotFoundException;
import com.kbalazsworks.stackjudge.domain.common_module.repositories.AbstractRepository;
import com.kbalazsworks.stackjudge.domain.company_module.entities.Company;
import com.kbalazsworks.stackjudge.domain.company_module.exceptions.CompanyException;
import com.kbalazsworks.stackjudge.domain.review_module.enums.NavigationEnum;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.SortField;
import org.jooq.Table;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.field;

@Repository
@Log4j2
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

    public long create(@NonNull Company company)
    {
        Record1<Long> newIdRecord = getQueryBuilder()
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
                company.getName(),
                company.getDomain(),
                company.getCompanySizeId(),
                company.getItSizeId(),
                company.getLogoPath(),
                company.getCreatedAt(),
                company.getCreatedBy()
            )
            .returningResult(companyTable.ID)
            .fetchOne();

        if (null == newIdRecord)
        {
            log.error("Company creation failed: {}", company);

            throw new CompanyException("Company creation failed.");
        }

        return newIdRecord.getValue(companyTable.ID);
    }

    @Cacheable("companies")
    public List<Company> search(long seekId, int limit)
    {
        return getQueryBuilder()
            .selectFrom(companyTable)
            .where(companyTable.ID.greaterOrEqual(seekId))
            .orderBy(companyTable.ID)
            .limit(limit)
            .fetchInto(Company.class);
    }

    @Cacheable("companies")
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
        Long count = getQueryBuilder()
            .selectCount()
            .from(companyTable)
            .fetchOneInto(Long.class);

        return Optional.ofNullable(count).orElse(0L);
    }

    public long countRecordsBeforeId(long id)
    {
        Long count = getQueryBuilder()
            .selectCount()
            .from(companyTable)
            .where(companyTable.ID.lessThan(id))
            .fetchOneInto(Long.class);

        return Optional.ofNullable(count).orElse(0L);
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
