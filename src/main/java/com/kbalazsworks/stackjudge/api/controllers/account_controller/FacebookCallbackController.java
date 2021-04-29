package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.services.FacebookCallbackService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("AccountFacebookCallbackAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
public class FacebookCallbackController
{
    private FacebookCallbackService facebookCallbackService;

    @Autowired
    public void setFacebookCallbackService(FacebookCallbackService facebookCallbackService)
    {
        this.facebookCallbackService = facebookCallbackService;
    }

    @GetMapping("/facebook-callback")
    public HttpEntity<ResponseData<String>> facebookCallback(
        @RequestParam("code") String code,
        @RequestParam("state") String state
    ) throws ApiException, Exception //@todo: this will be only httpexception
    {
        return new ResponseEntityBuilder<String>().data(facebookCallbackService.handler(code)).build();
    }
}
