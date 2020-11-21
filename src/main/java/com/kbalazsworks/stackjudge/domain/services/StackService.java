package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.Group;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroupRecord;
import com.kbalazsworks.stackjudge.domain.repositories.StackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StackService
{
    private StackRepository stackRepository;

    @Autowired
    public void setStackRepository(StackRepository stackRepository)
    {
        this.stackRepository = stackRepository;
    }

    public void create(Group group)
    {
        stackRepository.create(group);
    }

    // @todo: test
    public List<RecursiveGroupRecord> recursiveSearch(long companyId)
    {
        return stackRepository.recursiveSearch(companyId);
    }

    // @todo: test
    public Map<Long, Integer> countStacks(List<Long> companyIds)
    {
        return stackRepository.countStacks(companyIds);
    }

    // @todo: test
    public Map<Long, Integer> countTeams(List<Long> companyIds)
    {
        return stackRepository.countTeams(companyIds);
    }
}
