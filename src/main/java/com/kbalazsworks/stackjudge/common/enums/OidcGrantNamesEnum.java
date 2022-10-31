package com.kbalazsworks.stackjudge.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OidcGrantNamesEnum
{
    NOTIFICATION__SEND_PUSH("notification.send_push"),
    SJ__AWS__EC2("sj.aws.ec2"),
    SJ__AWS__SES("sj.aws.ses");

    final private String value;
}
