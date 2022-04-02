package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.kbalazsworks.stackjudge.api.services.facebook_services.FacebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

@RestController("AccountFacebookCallbackAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class FacebookCallbackController
{
    private final FacebookService facebookService;

    @GetMapping("/facebook-callback")
    public RedirectView facebookCallback(
        @RequestParam("code") String code,
        @RequestParam("state") String state,
        HttpServletResponse response
    ) throws Exception
    {
        return new RedirectView(facebookService.registerAndLoginAndRedirect(response, code, state));
    }
}
