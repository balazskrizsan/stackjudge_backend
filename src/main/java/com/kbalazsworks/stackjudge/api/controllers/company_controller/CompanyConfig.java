package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import java.util.ArrayList;
import java.util.List;

public class CompanyConfig
{
    public static final String CONTROLLER_URI        = "/company";
    public static final String GET_SECURITY_PATH     = "/**";
    public static final String SEARCH_SECURITY_PATH  = "";
    public static final String POST_OWN_REQUEST_PATH = "/own-request";
    public static final String GET_OWN_COMPLETE_PATH = "/own-complete";

    public static final List<String> openapiFrontendUrls = new ArrayList<>()
    {{
        add("/company/**");
    }};
}
