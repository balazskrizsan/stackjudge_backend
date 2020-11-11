package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.requests.company_request.CompanyCompositeRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.services.RequestMapperService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import com.kbalazsworks.stackjudge.session.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("CompanyPostAction")
@RequestMapping(CompanyConfig.CONTROLLER_URI)
public class PostAction
{
    private CompanyService companyService;
    private SessionService sessionService;

    @Autowired
    public void setCompanyService(CompanyService companyService)
    {
        this.companyService = companyService;
    }

    @Autowired
    public void setSessionService(SessionService sessionService)
    {
        this.sessionService = sessionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData<String>> action(@RequestBody CompanyCompositeRequest request) throws ApiException
    {
        new JavaxValidatorService<CompanyCompositeRequest>().validate(request);

        companyService.create(
            RequestMapperService.mapToRecord(request.company(), sessionService.getSessionState()),
            RequestMapperService.mapToRecord(request.address(), sessionService.getSessionState())
        );

        ResponseEntityBuilder<String> responseEntityBuilder = new ResponseEntityBuilder<>();

        responseEntityBuilder.setData(null);

        return responseEntityBuilder.build();
    }
}
