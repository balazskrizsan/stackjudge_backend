package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.company_request.GetRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("CompanySearchAction")
@RequestMapping(CompanyConfig.CONTROLLER_URI)
public class SearchAction
{
    private CompanyService companyService;

    @Autowired
    public void setCompanyService(CompanyService companyService)
    {
        this.companyService = companyService;
    }

    @GetMapping()
    public ResponseEntity<ResponseData<List<Company>>> action(GetRequest request) throws Exception
    {
        ResponseEntityBuilder<List<Company>> responseEntityBuilder = new ResponseEntityBuilder<>();

        responseEntityBuilder.setData(companyService.search());

        return responseEntityBuilder.build();
    }
}

