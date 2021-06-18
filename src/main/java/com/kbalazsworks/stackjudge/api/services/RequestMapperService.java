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
import lombok.NonNull;

public class RequestMapperService
{
    public static Group mapToRecord(@NonNull GroupCreateRequest request, @NonNull State state)
    {
        return new Group(
            null,
            request.parentId(),
            request.companyId(),
            request.name(),
            request.typeId(),
            request.membersOnStackId(),
            state.now(),
            state.user().getId()
        );
    }

    public static Company mapToRecord(@NonNull CompanyCreateRequest request, @NonNull State state)
    {
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

    public static Address mapToRecord(@NonNull AddressCreateRequest request, @NonNull State state)
    {
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

    public static Review mapToRecord(@NonNull ReviewCreateRequest request, @NonNull State state)
    {
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
