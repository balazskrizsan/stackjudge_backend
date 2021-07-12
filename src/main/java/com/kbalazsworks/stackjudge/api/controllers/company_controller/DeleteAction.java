package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.company_request.DeleteRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("CompanyDeleteAction")
@RequestMapping(CompanyConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class DeleteAction
{
    private final CompanyService companyService;

    @DeleteMapping("{companyId}")
    public ResponseEntity<ResponseData<String>> action(DeleteRequest request) throws Exception
    {
        new JavaxValidatorService<DeleteRequest>().validate(request);

        companyService.delete(request.getCompanyId());

        return new ResponseEntityBuilder<String>().data(null).build();
    }
}
