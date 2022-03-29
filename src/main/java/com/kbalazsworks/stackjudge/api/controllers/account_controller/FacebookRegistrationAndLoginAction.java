package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.kbalazsworks.stackjudge.api.services.facebook_services.FacebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController("FacebookRegistrationAndLoginAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class FacebookRegistrationAndLoginAction
{
    private final FacebookService facebookService;

    @GetMapping("/facebook/registration-and-login")
    public RedirectView action() throws Exception
    {
        return new RedirectView(facebookService.redirectToRegistrationAndLogin());
    }
}
