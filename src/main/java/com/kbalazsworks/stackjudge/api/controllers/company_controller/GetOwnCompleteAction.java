package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.requests.company_request.GetOwnCompleteRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.services.RequestMapperService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.company.OwnCompleteService;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("CompanyGetOwnCompleteAction")
@RequestMapping(CompanyConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class GetOwnCompleteAction
{
    private final OwnCompleteService ownCompleteService;
    private final StateService       stateService;

    @GetMapping(CompanyConfig.GET_OWN_COMPLETE_PATH + "/{code}")
    public ResponseEntity<ResponseData<String>> action(
        GetOwnCompleteRequest ownCompleteRequest
    ) throws ApiException, IOException
    {
        new JavaxValidatorService<GetOwnCompleteRequest>().validate(ownCompleteRequest);

        ownCompleteService.complete(RequestMapperService.mapToRecord(ownCompleteRequest), stateService.getState());

        return new ResponseEntityBuilder<String>().data(null).build();
    }
}
