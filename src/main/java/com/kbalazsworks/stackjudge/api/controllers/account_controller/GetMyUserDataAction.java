package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.account_requests.GetByReviewIdRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("GetMyUserDataAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class GetMyUserDataAction
{
    private final AccountService accountService;
    private final StateService   stateService;

    @GetMapping("/get-my-user-data")
    public ResponseEntity<ResponseData<User>> action() throws Exception
    {
        return new ResponseEntityBuilder<User>().data(accountService.getCurrentUser()).build();
    }
}
