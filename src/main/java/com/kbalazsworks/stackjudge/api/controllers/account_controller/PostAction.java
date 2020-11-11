package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AccountPostAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
public class PostAction
{
    @PostMapping("/registration")
    public ResponseEntity<ResponseData<String>> action() throws Exception
    {
        ResponseEntityBuilder<String> responseEntityBuilder = new ResponseEntityBuilder<>();

        responseEntityBuilder.setData("");

        return responseEntityBuilder.build();
    }
}
