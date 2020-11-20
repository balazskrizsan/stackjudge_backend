package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.enums.stack_table.TypeEnum;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveStackRecord;
import com.kbalazsworks.stackjudge.domain.entities.Stack;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.val;

@Repository
public class StackRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.Stack stackTable =
        com.kbalazsworks.stackjudge.db.tables.Stack.STACK;

    public void create(Stack stack)
    {
        createQueryBuilder()
            .insertInto(
                stackTable,
                stackTable.COMPANY_ID,
                stackTable.PARENT_ID,
                stackTable.TYPE_ID,
                stackTable.NAME,
                stackTable.MEMBERS_ON_STACK_ID,
                stackTable.CREATED_AT,
                stackTable.CREATED_BY
            )
            .values(
                stack.companyId(),
                stack.parentId(),
                stack.typeId(),
                stack.name(),
                stack.membersOnStackId(),
                stack.createdAt(),
                stack.createdBy()
            )
            .execute();
    }

    public List<RecursiveStackRecord> recursiveSearch(long companyId)
    {
        return createQueryBuilder()
            .resultQuery(
                "WITH RECURSIVE rec(id, name, parent_id, depth, path) AS ("
                    + "     SELECT S.id, S.name, S.parent_id, 1::INT AS depth, S.id::TEXT AS path FROM stack AS S WHERE S.company_id = {0} AND parent_id IS NULL"
                    + "     UNION ALL"
                    + "     SELECT SR.id, SR.name, SR.parent_id, R.depth + 1 AS depth, (R.path || '>' || SR.id::TEXT) FROM rec AS R, stack AS SR WHERE SR.parent_id = R.id"
                    + " )"
                    + " SELECT * FROM rec;",
                val(companyId)
            )
            .fetch()
            .into(RecursiveStackRecord.class);
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
            .select(stackTable.COMPANY_ID, count())
            .from(stackTable)
            .where(
                stackTable.COMPANY_ID.in(companyIds)
                    .and(stackTable.TYPE_ID.eq(type.getValue()))
            )
            .groupBy(stackTable.COMPANY_ID)
            .fetchMap(stackTable.COMPANY_ID, count());

        return Collections.unmodifiableMap(result);
    }
}
