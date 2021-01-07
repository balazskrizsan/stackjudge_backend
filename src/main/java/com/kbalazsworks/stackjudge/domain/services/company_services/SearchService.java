package com.kbalazsworks.stackjudge.domain.services.company_services;

import com.kbalazsworks.stackjudge.domain.services.GroupService;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroupTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService
{
    private GroupService groupService;

    @Autowired
    public void setGroupService(GroupService groupService)
    {
        this.groupService = groupService;
    }

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
                    companyGroups.put(key, new ArrayList<>()
                    {{
                        add(recursiveGroupTree);
                    }});

                    return;
                }

                groupTrees.add(recursiveGroupTree);
            }
        );

        return companyGroups;
    }

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
