package com.kbalazsworks.stackjudge.stackjudge_aws_sdk.open_sdk_module.value_objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public record OpenSdkStdResponse<T>(HttpStatus status, HttpHeaders headers, T data)
{
};
