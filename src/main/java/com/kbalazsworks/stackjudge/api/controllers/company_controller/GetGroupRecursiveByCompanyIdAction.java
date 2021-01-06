package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.requests.company_request.GetStackRecursiveByCompanyIdRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroup;
import com.kbalazsworks.stackjudge.domain.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("CompanyGetGroupRecursiveByCompanyIdAction")
@RequestMapping(CompanyConfig.CONTROLLER_URI)
public class GetGroupRecursiveByCompanyIdAction
{
    private GroupService groupService;

    @Autowired
    public void setStackService(GroupService groupService)
    {
        this.groupService = groupService;
    }

    @GetMapping("{companyId}/group/recursive")
    public ResponseEntity<ResponseData<List<RecursiveGroup>>> action(GetStackRecursiveByCompanyIdRequest request) throws ApiException
    {
        new JavaxValidatorService<GetStackRecursiveByCompanyIdRequest>().validate(request);

        ResponseEntityBuilder<List<RecursiveGroup>> responseEntityBuilder = new ResponseEntityBuilder<>();

        responseEntityBuilder.setData(groupService.recursiveSearch(request.getCompanyId()));

        return responseEntityBuilder.build();
    }
}