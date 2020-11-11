package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveStackRecord;
import com.kbalazsworks.stackjudge.domain.entities.Stack;
import com.kbalazsworks.stackjudge.domain.repositories.StackRepository;
import com.kbalazsworks.stackjudge.session.entities.SessionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StackService
{
    private StackRepository stackRepository;

    @Autowired
    public void setStackRepository(StackRepository stackRepository)
    {
        this.stackRepository = stackRepository;
    }

    public void create(Stack stack)
    {
        stackRepository.create(stack);
    }

    public List<RecursiveStackRecord> recursiveSearch(long companyId)
    {
        return stackRepository.recursiveSearch(companyId);
    }
}
