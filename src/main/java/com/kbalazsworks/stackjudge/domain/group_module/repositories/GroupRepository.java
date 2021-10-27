package com.kbalazsworks.stackjudge.domain.group_module.repositories;

import com.kbalazsworks.stackjudge.domain.common_module.repositories.AbstractRepository;
import com.kbalazsworks.stackjudge.domain.group_module.enums.TypeEnum;
import com.kbalazsworks.stackjudge.domain.group_module.value_objects.RecursiveGroup;
import com.kbalazsworks.stackjudge.domain.group_module.entities.Group;
import lombok.NonNull;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;

@Repository
public class GroupRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.Group groupTable =
        com.kbalazsworks.stackjudge.db.tables.Group.GROUP;

    public void create(@NonNull Group group)
    {
        getQueryBuilder()
            .insertInto(
                groupTable,
                groupTable.COMPANY_ID,
                groupTable.PARENT_ID,
                groupTable.ADDRESS_ID,
                groupTable.TYPE_ID,
                groupTable.NAME,
                groupTable.MEMBERS_ON_GROUP_ID,
                groupTable.CREATED_AT,
                groupTable.CREATED_BY
            )
            .values(
                group.companyId(),
                group.parentId(),
                group.addressId(),
                group.typeId(),
                group.name(),
                group.membersOnGroupId(),
                group.createdAt(),
                group.createdBy()
            )
            .execute();
    }

    public List<RecursiveGroup> recursiveSearch(List<Long> companyId)
    {
        return getQueryBuilder()
            .resultQuery(
                "WITH RECURSIVE rec(id, name, type_id, company_id, address_id, parent_id, depth, path) AS ("
                    + "     SELECT S.id, S.name, S.type_id, S.company_id, S.address_id, S.parent_id, 1::INT AS depth, S.id::TEXT AS path FROM \"group\" AS S WHERE S.company_id IN ({0}) AND parent_id IS NULL"
                    + "     UNION ALL"
                    + "     SELECT SR.id, SR.name, SR.type_id, SR.company_id, SR.address_id, SR.parent_id, R.depth + 1 AS depth, (R.path || '>' || SR.id::TEXT) AS path FROM rec AS R, \"group\" AS SR WHERE SR.parent_id = R.id"
                    + " )"
                    + " SELECT * FROM rec"
                    + " ORDER BY path;",
                list(companyId.stream().map(DSL::val).collect(Collectors.toList()))
            )
            .fetchInto(RecursiveGroup.class);
    }

    public Map<Long, Integer> countStacks(List<Long> companyIds)
    {
        return countTeamsOrStacks(companyIds, TypeEnum.STACK);
    }

    public Map<Long, Integer> countTeams(List<Long> companyIds)
    {
        return countTeamsOrStacks(companyIds, TypeEnum.TEAM);
    }

    private Map<Long, Integer> countTeamsOrStacks(List<Long> companyIds, @NonNull TypeEnum type)
    {
        Map<Long, Integer> result = getQueryBuilder()
            .select(groupTable.COMPANY_ID, count())
            .from(groupTable)
            .where(
                groupTable.COMPANY_ID.in(companyIds)
                    .and(groupTable.TYPE_ID.eq(type.getValue()))
            )
            .groupBy(groupTable.COMPANY_ID)
            .fetchMap(groupTable.COMPANY_ID, count());

        return Collections.unmodifiableMap(result);
    }
}
