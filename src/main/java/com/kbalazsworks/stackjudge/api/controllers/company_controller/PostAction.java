package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.requests.company_request.AddressCreateRequest;
import com.kbalazsworks.stackjudge.api.requests.company_request.CompanyCompositeRequest;
import com.kbalazsworks.stackjudge.api.requests.company_request.CompanyCreateRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.services.RequestMapperService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import com.kbalazsworks.stackjudge.session.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<String>> action(
        @RequestPart("company") String company,
        @RequestPart("address") String address,
        @RequestPart(value = "companyLogo", required = false) MultipartFile companyLogo
    ) throws ApiException, IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();

        CompanyCompositeRequest request = new CompanyCompositeRequest(
            objectMapper.readValue(company, CompanyCreateRequest.class),
            objectMapper.readValue(address, AddressCreateRequest.class)
        );

        new JavaxValidatorService<CompanyCompositeRequest>().validate(request);

        companyService.create(
            RequestMapperService.mapToRecord(request.company(), sessionService.getSessionState()),
            RequestMapperService.mapToRecord(request.address(), sessionService.getSessionState()),
            companyLogo
        );

        ResponseEntityBuilder<String> responseEntityBuilder = new ResponseEntityBuilder<>();
        responseEntityBuilder.setData(null);
        return responseEntityBuilder.build();
    }
}
