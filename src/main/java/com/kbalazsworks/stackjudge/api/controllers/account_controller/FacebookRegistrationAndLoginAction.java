package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.kbalazsworks.stackjudge.api.services.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController("FacebookRegistrationAndLoginAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
public class FacebookRegistrationAndLoginAction
{
    private FacebookService facebookService;

    @Autowired
    public void setFacebookService(FacebookService facebookService)
    {
        this.facebookService = facebookService;
    }

    @GetMapping("/facebook/registration-and-login")
    public RedirectView action() throws Exception
    {
        return new RedirectView(facebookService.registrationAndLogin());
    }
}
