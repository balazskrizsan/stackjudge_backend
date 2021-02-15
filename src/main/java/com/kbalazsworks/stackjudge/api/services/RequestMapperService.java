package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.api.requests.company_request.AddressCreateRequest;
import com.kbalazsworks.stackjudge.api.requests.company_request.CompanyCreateRequest;
import com.kbalazsworks.stackjudge.api.requests.group_request.GroupCreateRequest;
import com.kbalazsworks.stackjudge.api.requests.review_requests.ReviewCreateRequest;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.entities.Group;
import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.state.entities.State;

public class RequestMapperService
{
    public static Group mapToRecord(GroupCreateRequest request, State state) {
        return new Group(
            null,
            request.companyId(),
            request.parentId(),
            request.typeId(),
            request.name(),
            request.membersOnStackId(),
            state.now(),
            state.user().getId()
        );
    }

    public static Company mapToRecord(CompanyCreateRequest request, State state) {
        return new Company(
            null,
            request.name(),
            request.companySizeId(),
            request.itSizeId(),
            "",
            state.now(),
            state.user().getId()
        );
    }

    public static Address mapToRecord(AddressCreateRequest request, State state) {
        return new Address(
            null,
            null,
            request.fullAddress(),
            request.markerLat(),
            request.markerLng(),
            request.manualMarkerLat(),
            request.manualMarkerLng(),
            state.now(),
            state.user().getId()
        );
    }

    public static Review mapToRecord(ReviewCreateRequest request, State state) {
        return new Review(
            null,
            request.group_id(),
            request.visibility(),
            request.rate(),
            request.review(),
            state.now(),
            state.user().getId()
        );
    }
}
