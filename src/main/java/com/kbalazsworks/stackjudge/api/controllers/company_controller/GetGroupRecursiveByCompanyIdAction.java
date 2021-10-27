package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.requests.company_request.GetStackRecursiveByCompanyIdRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.group_module.services.GroupService;
import com.kbalazsworks.stackjudge.domain.group_module.value_objects.RecursiveGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("CompanyGetGroupRecursiveByCompanyIdAction")
@RequestMapping(CompanyConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class GetGroupRecursiveByCompanyIdAction
{
    private final GroupService groupService;

    @GetMapping("{companyId}/group/recursive")
    public ResponseEntity<ResponseData<List<RecursiveGroup>>> action(GetStackRecursiveByCompanyIdRequest request) throws ApiException
    {
        new JavaxValidatorService<GetStackRecursiveByCompanyIdRequest>().validate(request);

        return new ResponseEntityBuilder<List<RecursiveGroup>>()
            .data(groupService.recursiveSearch(request.getCompanyId()))
            .build();
    }
}