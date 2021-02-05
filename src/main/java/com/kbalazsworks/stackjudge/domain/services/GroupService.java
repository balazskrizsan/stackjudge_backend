package com.kbalazsworks.stackjudge.domain.services;

import com.google.common.collect.Lists;
import com.kbalazsworks.stackjudge.domain.entities.Group;
import com.kbalazsworks.stackjudge.domain.repositories.GroupRepository;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroup;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroupTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    //@todo: test
    public List<RecursiveGroup> recursiveSearch(List<Long> companyId)
    {
        return groupRepository.recursiveSearch(companyId);
    }

    //@todo: mock test
    public List<RecursiveGroup> recursiveSearch(long companyId)
    {
        return recursiveSearch(List.of(companyId));
    }

    public Map<Long, Integer> countStacks(List<Long> companyIds)
    {
        return groupRepository.countStacks(companyIds);
    }

    public Map<Long, Integer> countTeams(List<Long> companyIds)
    {
        return groupRepository.countTeams(companyIds);
    }

    public List<RecursiveGroupTree> generateTreeStructure(List<RecursiveGroup> recursiveGroups)
    {
        List<RecursiveGroupTree>            recursiveGroupTrees = new ArrayList<>();
        Map<Long, List<RecursiveGroupTree>> children            = new HashMap<>();

        int maxDepth = recursiveGroups.stream().map(RecursiveGroup::depth).max(Integer::compareTo).orElse(0);

        for (int depth = maxDepth; depth > 0; depth--)
        {
            int finalDepth = depth;
            recursiveGroups.stream().filter(g -> g.depth() == finalDepth).forEach(
                recursiveGroup ->
                {
                    List<RecursiveGroupTree> childrenOfCurrents = children.get(recursiveGroup.id());

                    RecursiveGroupTree newChild = new RecursiveGroupTree(recursiveGroup, childrenOfCurrents);

                    if (recursiveGroup.depth() == 1)
                    {
                        recursiveGroupTrees.add(newChild);

                        return;
                    }

                    List<RecursiveGroupTree> parent = children.get(recursiveGroup.parentId());
                    if (null == parent)
                    {
                        children.put(recursiveGroup.parentId(), Lists.newArrayList(newChild));

                        return;
                    }

                    parent.add(newChild);
                });
        }

        return recursiveGroupTrees;
    }
}
