package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import java.util.ArrayList;
import java.util.List;

public class AccountConfig
{
    public static final String CONTROLLER_URI                              = "/account";
    public static final String ACTION_GET_BY_REVIEW_ID                     = "get-by-review-id/{reviewId}";
    public static final String REGISTRATION_AND_LOGIN_SECURITY_PATH        = "/facebook/registration-and-login";
    public static final String FACEBOOK_CALLBACK_SECURITY_PATH             = "/facebook-callback";
    public static final String GET_PUSHOVER_TOKEN_BY_USER_ID_SECURITY_PATH = "/**";

    public static final List<String> openapiFrontendUrls = new ArrayList<>()
    {{
        add("/account/get-by-review-id/**");
    }};
}
