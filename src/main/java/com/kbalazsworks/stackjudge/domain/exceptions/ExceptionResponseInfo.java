package com.kbalazsworks.stackjudge.domain.exceptions;

public class ExceptionResponseInfo
{
    // COMPANY: 1xxx
    public static final String CompanyCreationFailedMsg       = "Company creation failed.";
    public static final int    CompanyCreationFailedErrorCode = 1000;

    public static final String CompanyNotFoundMsg       = "Company not found.";
    public static final int    CompanyNotFoundErrorCode = 1001;

    // GROUP: 2xxx
    // ADDRESS: 3xxx
    // REVIEW: 4xxx
    // MAPS: 5xxx
    public static final String MapNotFoundMsg       = "Map not found.";
    public static final int    MapNotFoundErrorCode = 5000;
    // NOTIFICATION: 6xxx
}
