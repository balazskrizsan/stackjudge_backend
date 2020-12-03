package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.company_request.SearchRequest;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.enums.paginator.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanySearchServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

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
    public ResponseEntity<ResponseData<CompanySearchServiceResponse>> action(@RequestPayload SearchRequest request)
    throws Exception
    {
        CompanySearchServiceResponse response = companyService.search(
            request.seekId(),
            request.limit(),
            request.requestRelationIds(),
            NavigationEnum.getByValue(request.navigationId())
        );

        ResponseEntityBuilder<CompanySearchServiceResponse> responseEntityBuilder = new ResponseEntityBuilder<>();

        responseEntityBuilder.setData(response);

        return responseEntityBuilder.build();
    }
}
