package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.requests.company_request.PostOwnRequestRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.services.RequestMapperService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.company_service.OwnRequestService;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("CompanyPostOwnRequestAction")
@RequestMapping(CompanyConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class PostOwnRequestAction
{
    private final OwnRequestService ownRequestService;
    private final StateService      stateService;

    @PostMapping(path = CompanyConfig.POST_OWN_REQUEST_PATH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<String>> action(PostOwnRequestRequest postOwnRequestRequest) throws ApiException, IOException
    {
        new JavaxValidatorService<PostOwnRequestRequest>().validate(postOwnRequestRequest);

        ownRequestService.own(RequestMapperService.mapToRecord(postOwnRequestRequest), stateService.getState());

        return new ResponseEntityBuilder<String>().data(null).build();
    }
}
