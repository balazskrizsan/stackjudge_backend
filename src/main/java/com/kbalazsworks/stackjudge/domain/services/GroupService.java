package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.Group;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroupRecord;
import com.kbalazsworks.stackjudge.domain.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GroupService
{
    private GroupRepository groupRepository;

    @Autowired
    public void setStackRepository(GroupRepository groupRepository)
    {
        this.groupRepository = groupRepository;
    }

    public void create(Group group)
    {
        groupRepository.create(group);
    }

    public List<RecursiveGroupRecord> recursiveSearch(long companyId)
    {
        return groupRepository.recursiveSearch(companyId);
    }

    public Map<Long, Integer> countStacks(List<Long> companyIds)
    {
        return groupRepository.countStacks(companyIds);
    }

    public Map<Long, Integer> countTeams(List<Long> companyIds)
    {
        return groupRepository.countTeams(companyIds);
    }
}
