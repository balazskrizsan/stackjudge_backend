package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.company_request.GetPushoverTokenByUserIdRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.state.entities.PushoverInfo;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("GetPushoverTokenByUserIdAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class GetPushoverTokenByUserIdAction
{
    private final AccountService accountService;

    @GetMapping("{userId}/pushover/token-by-user-id")
    public ResponseEntity<ResponseData<PushoverInfo>> action(GetPushoverTokenByUserIdRequest request)
    throws Exception
    {
        new JavaxValidatorService<GetPushoverTokenByUserIdRequest>().validate(request);

        return new ResponseEntityBuilder<PushoverInfo>()
            .data(accountService.findPushoverDataById(request.getIdsUserId()))
            .build();
    }
}
