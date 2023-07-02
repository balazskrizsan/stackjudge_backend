package com.kbalazsworks.stackjudge.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OidcGrantNamesEnum
{
    NOTIFICATION__SEND_PUSH("notification.send_push"),
    XC__SJ__AWS("xc/sj.aws"),
    SJ__AWS("sj.aws"),
    SJ__AWS__SES("sj.aws.ses"),
    SJ__IDS__API("sj.ids.api");

    final private String value;
}
