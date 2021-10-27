package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.company_request.GetRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.company_module.services.CompanyService;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.CompanyGetServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("CompanyGetAction")
@RequestMapping(CompanyConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class GetAction
{
    private final CompanyService companyService;

    @GetMapping("{companyId}")
    public ResponseEntity<ResponseData<CompanyGetServiceResponse>> action(GetRequest request) throws Exception
    {
        new JavaxValidatorService<GetRequest>().validate(request);

        return new ResponseEntityBuilder<CompanyGetServiceResponse>()
            .data(companyService.get(request.getCompanyId(), request.getRequestRelationIds()))
            .build();
    }
}
