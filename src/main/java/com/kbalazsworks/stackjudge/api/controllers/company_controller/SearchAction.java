package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.company_request.SearchRequest;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.review_module.enums.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.company_module.services.CompanyService;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanySearchServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

@RestController("CompanySearchAction")
@RequestMapping(CompanyConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class SearchAction
{
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<ResponseData<CompanySearchServiceResponse>> action(@RequestPayload SearchRequest request)
        throws Exception
    {
        Short navigation = request.navigationId();
        CompanySearchServiceResponse response = companyService.search(
            request.seekId(),
            request.limit(),
            request.requestRelationIds(),
            navigation == null ? null : NavigationEnum.getByValue(request.navigationId())
        );

        return new ResponseEntityBuilder<CompanySearchServiceResponse>().data(response).build();
    }
}
