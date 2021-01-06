package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.enums.group_table.TypeEnum;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroup;
import com.kbalazsworks.stackjudge.domain.entities.Group;
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

    public void create(Group group)
    {
        createQueryBuilder()
            .insertInto(
                groupTable,
                groupTable.COMPANY_ID,
                groupTable.PARENT_ID,
                groupTable.TYPE_ID,
                groupTable.NAME,
                groupTable.MEMBERS_ON_GROUP_ID,
                groupTable.CREATED_AT,
                groupTable.CREATED_BY
            )
            .values(
                group.companyId(),
                group.parentId(),
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
        return createQueryBuilder()
            .resultQuery(
                "WITH RECURSIVE rec(id, name, company_id, parent_id, depth, path) AS ("
                    + "     SELECT S.id, S.name, S.company_id, S.parent_id, 1::INT AS depth, S.id::TEXT AS path FROM \"group\" AS S WHERE S.company_id IN ({0}) AND parent_id IS NULL"
                    + "     UNION ALL"
                    + "     SELECT SR.id, SR.name, SR.company_id, SR.parent_id, R.depth + 1 AS depth, (R.path || '>' || SR.id::TEXT) FROM rec AS R, \"group\" AS SR WHERE SR.parent_id = R.id"
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

    private Map<Long, Integer> countTeamsOrStacks(List<Long> companyIds, TypeEnum type)
    {
        Map<Long, Integer> result = createQueryBuilder()
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
