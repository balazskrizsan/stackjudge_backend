package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.services.FacebookCallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

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
    public RedirectView facebookCallback(
        @RequestParam("code") String code,
        @RequestParam("state") String state
    ) throws ApiException, Exception //@todo: this will be only httpexception
    {
        return new RedirectView("http://localhost:4200/account/login/".concat(facebookCallbackService.handler(code)));
    }
}