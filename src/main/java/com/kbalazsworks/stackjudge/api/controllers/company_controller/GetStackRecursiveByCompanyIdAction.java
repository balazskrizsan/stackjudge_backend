package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.requests.company_request.GetStackRecursiveByCompanyIdRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveStackRecord;
import com.kbalazsworks.stackjudge.domain.services.StackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("CompanyGetStackRecursiveByCompanyIdAction")
@RequestMapping(CompanyConfig.CONTROLLER_URI)
public class GetStackRecursiveByCompanyIdAction
{
    private StackService stackService;

    @Autowired
    public void setStackService(StackService stackService)
    {
        this.stackService = stackService;
    }

    @GetMapping("{companyId}/stack/recursive")
    public ResponseEntity<ResponseData<List<RecursiveStackRecord>>> action(GetStackRecursiveByCompanyIdRequest request) throws ApiException
    {
        new JavaxValidatorService<GetStackRecursiveByCompanyIdRequest>().validate(request);

        ResponseEntityBuilder<List<RecursiveStackRecord>> responseEntityBuilder = new ResponseEntityBuilder<>();

        responseEntityBuilder.setData(stackService.recursiveSearch(request.getCompanyId()));

        return responseEntityBuilder.build();
    }
}