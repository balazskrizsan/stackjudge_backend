package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.enums.paginator.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.CompanyException;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jooq.impl.DSL.field;

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
            .orderBy(companyTable.ID)
            .seek(seekId - 1)
            .limit(limit)
            .fetchInto(Company.class);
    }

    public List<Company> search(NavigationEnum navigation, int limit)
    {
        return createQueryBuilder()
            .selectFrom(companyTable)
            .orderBy(companyTable.ID)
            .seek(getSeekSubQueryForSeekId(limit, navigation))
            .limit(limit)
            .fetchInto(Company.class);
    }

    public List<Company> search(long seekId, NavigationEnum navigation, int limit)
    {
        String innerCompanyAlias = "inner_company";
        com.kbalazsworks.stackjudge.db.tables.Company innerCompany
            = com.kbalazsworks.stackjudge.db.tables.Company.COMPANY.as(innerCompanyAlias);

        return createQueryBuilder()
            .selectFrom(companyTable)
            .orderBy(companyTable.ID)
            .seek(
                field(
                    createQueryBuilder()
                        .select(innerCompany.ID)
                        .from(
                            createQueryBuilder()
                                .select(companyTable.ID)
                                .from(companyTable)
                                .where(companyTable.ID.greaterThan(seekId))
                                .limit(limit)
                                .asTable(innerCompanyAlias)
                        )
                        .orderBy(innerCompany.ID)
                        .limit(1)
                )
            )
            .limit(limit)
            .fetchInto(Company.class);
    }

    private Field<Long> getSeekSubQueryForSeekId(int limit, NavigationEnum navigation)
    {
        String innerCompanyAlias = "inner_company";
        com.kbalazsworks.stackjudge.db.tables.Company innerCompany
            = com.kbalazsworks.stackjudge.db.tables.Company.COMPANY.as(innerCompanyAlias);

        if (NavigationEnum.SECOND == navigation)
        {
            Table<Record1<Long>> selectAllUntilTheFirstElementOnSecondPage =
                createQueryBuilder().select(companyTable.ID).from(companyTable).limit(limit).asTable(innerCompanyAlias);

            return field(
                createQueryBuilder()
                    .select(innerCompany.ID)
                    .from(selectAllUntilTheFirstElementOnSecondPage)
                    .orderBy(innerCompany.ID.desc())
                    .limit(1)
            );
        }

        if (NavigationEnum.LAST_MINUS_1 == navigation)
        {
            Table<Record1<Long>> selectLastPageItemsInDesc = createQueryBuilder()
                .select(companyTable.ID)
                .from(companyTable)
                .orderBy(companyTable.ID.desc())
                .limit((limit * 2) + 1)
                .asTable(innerCompanyAlias);

            return field(
                createQueryBuilder()
                    .select(innerCompany.ID)
                    .from(selectLastPageItemsInDesc)
                    .orderBy(innerCompany.ID)
                    .limit(1)
            );
        }

        if (NavigationEnum.LAST == navigation)
        {
            Table<Record1<Long>> selectLastPageItemsInDesc = createQueryBuilder()
                .select(companyTable.ID)
                .from(companyTable)
                .orderBy(companyTable.ID.desc())
                .limit(limit + 1)
                .asTable(innerCompanyAlias);

            return field(
                createQueryBuilder()
                    .select(innerCompany.ID)
                    .from(selectLastPageItemsInDesc)
                    .orderBy(innerCompany.ID)
                    .limit(1)
            );
        }

        //@todo: test
        throw new CompanyException("Seek sub query not found with enumId#".concat(navigation.getValue().toString()));
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
