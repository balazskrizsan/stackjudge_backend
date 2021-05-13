package com.kbalazsworks.stackjudge.api.controllers.test_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ProtectedAction")
@RequestMapping(TestConfig.CONTROLLER_URI)
public class ProtectedAction
{
    @GetMapping("/protected")
    public ResponseEntity<ResponseData<String>> action() throws ApiException
    {
        return new ResponseEntityBuilder<String>().data("Protected endpoint").build();
    }
}
