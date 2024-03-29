package com.kbalazsworks.stackjudge.domain.company_module.services.company_service;

import com.kbalazsworks.stackjudge.domain.group_module.services.GroupService;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.domain.group_module.value_objects.RecursiveGroupTree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService
{
    private final GroupService groupService;

    // @todo: test
    public Map<Long, List<RecursiveGroupTree>> getCompanyGroups(List<Long> companyIds)
    {
        Map<Long, List<RecursiveGroupTree>> companyGroups = new HashMap<>();

        groupService.generateTreeStructure(groupService.recursiveSearch(companyIds)).forEach(
            recursiveGroupTree ->
            {
                long key = recursiveGroupTree.recursiveGroup().companyId();

                List<RecursiveGroupTree> groupTrees = companyGroups.get(key);
                if (groupTrees == null)
                {
                    companyGroups.put(key, new ArrayList<>(List.of(recursiveGroupTree)));

                    return;
                }

                groupTrees.add(recursiveGroupTree);
            }
        );

        return companyGroups;
    }

    // @todo: test
    public Map<Long, CompanyStatistic> getStatistic(List<Long> companyIds)
    {
        Map<Long, CompanyStatistic> companyStatistics = new HashMap<>();

        Map<Long, Integer> stackInfo = groupService.countStacks(companyIds);
        Map<Long, Integer> teamInfo  = groupService.countTeams(companyIds);

        companyIds.forEach(id -> companyStatistics.put(id, new CompanyStatistic(
            id,
            stackInfo.getOrDefault(id, 0),
            teamInfo.getOrDefault(id, 0),
            0,
            0
        )));

        return companyStatistics;
    }
}
