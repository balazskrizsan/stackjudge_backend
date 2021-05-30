package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.kbalazsworks.stackjudge.api.services.FacebookCallbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController("AccountFacebookCallbackAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class FacebookCallbackController
{
    private final FacebookCallbackService facebookCallbackService;

    @GetMapping("/facebook-callback")
    public RedirectView facebookCallback(
        @RequestParam("code") String code,
        @RequestParam("state") String state
    )
    {
        return new RedirectView(facebookCallbackService.getJwtLoginUrl(code));
    }
}
