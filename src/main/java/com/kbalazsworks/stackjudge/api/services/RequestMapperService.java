package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.api.requests.company_request.AddressCreateRequest;
import com.kbalazsworks.stackjudge.api.requests.company_request.CompanyCreateRequest;
import com.kbalazsworks.stackjudge.api.requests.group_request.GroupCreateRequest;
import com.kbalazsworks.stackjudge.api.requests.maps_requests.GoogleStaticMapsRequest;
import com.kbalazsworks.stackjudge.api.requests.maps_requests.MarkerRequest;
import com.kbalazsworks.stackjudge.api.requests.review_requests.ReviewCreateRequest;
import com.kbalazsworks.stackjudge.domain.entities.*;
import com.kbalazsworks.stackjudge.domain.entities.google_static_maps.GoogleStaticMapMarker;
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
            request.addressId(),
            request.name(),
            request.typeId(),
            request.membersOnStackId(),
            state.now(),
            state.currentUser().getId()
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
            state.currentUser().getId()
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
            state.currentUser().getId()
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
            state.currentUser().getId()
        );
    }

    public static GoogleStaticMap mapToRecord(@NonNull GoogleStaticMapsRequest request)
    {
        return new GoogleStaticMap(
            request.sizeX(),
            request.sizeY(),
            request.scale(),
            request.zoom(),
            request.mapType(),
            request.centerLat(),
            request.centerLng()
        );
    }

    public static GoogleStaticMapMarker mapToRecord(@NonNull MarkerRequest request)
    {
        return new GoogleStaticMapMarker(
            request.getSize(),
            request.getColor(),
            request.getLabel(),
            request.getCenterLat(),
            request.getCenterLng()
        );
    }
}
