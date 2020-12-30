package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.api.requests.company_request.AddressCreateRequest;
import com.kbalazsworks.stackjudge.api.requests.company_request.CompanyCreateRequest;
import com.kbalazsworks.stackjudge.api.requests.stack_request.StackCreateRequest;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.entities.Group;
import com.kbalazsworks.stackjudge.session.entities.SessionState;

public class RequestMapperService
{
    public static Group mapToRecord(StackCreateRequest request, SessionState sessionState) {
        return new Group(
            null,
            request.companyId(),
            request.parentId(),
            request.typeId(),
            request.name(),
            request.membersOnStackId(),
            sessionState.getNow(),
            sessionState.getUser().getId()
        );
    }

    public static Company mapToRecord(CompanyCreateRequest request, SessionState sessionState) {
        return new Company(
            null,
            request.name(),
            request.companySizeId(),
            request.itSizeId(),
            "",
            sessionState.getNow(),
            sessionState.getUser().getId()
        );
    }

    public static Address mapToRecord(AddressCreateRequest request, SessionState sessionState) {
        return new Address(
            null,
            null,
            request.fullAddress(),
            request.markerLat(),
            request.markerLng(),
            request.manualMarkerLat(),
            request.manualMarkerLng(),
            sessionState.getNow(),
            sessionState.getUser().getId()
        );
    }
}
