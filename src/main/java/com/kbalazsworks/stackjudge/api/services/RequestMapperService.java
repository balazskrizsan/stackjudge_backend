package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.api.requests.OwnComplete;
import com.kbalazsworks.stackjudge.api.requests.company_request.AddressCreateRequest;
import com.kbalazsworks.stackjudge.api.requests.company_request.CompanyCreateRequest;
import com.kbalazsworks.stackjudge.api.requests.company_request.GetOwnCompleteRequest;
import com.kbalazsworks.stackjudge.api.requests.company_request.PostOwnRequestRequest;
import com.kbalazsworks.stackjudge.api.requests.group_request.GroupCreateRequest;
import com.kbalazsworks.stackjudge.api.requests.maps_requests.GoogleStaticMapMarkerRequest;
import com.kbalazsworks.stackjudge.api.requests.maps_requests.GoogleStaticMapRequest;
import com.kbalazsworks.stackjudge.api.requests.review_requests.ReviewCreateRequest;
import com.kbalazsworks.stackjudge.domain.address_module.entities.Address;
import com.kbalazsworks.stackjudge.domain.company_module.entities.Company;
import com.kbalazsworks.stackjudge.domain.group_module.entities.Group;
import com.kbalazsworks.stackjudge.domain.review_module.entities.Review;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapTypeEnum;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MarkerColorEnum;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MarkerSizeEnum;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.OwnRequest;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMapMarker;
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
            state.currentIdsUser().getIdsUserId()
        );
    }

    public static Company mapToRecord(@NonNull CompanyCreateRequest request, @NonNull State state)
    {
        return new Company(
            null,
            request.name(),
            request.domain(),
            request.companySizeId(),
            request.itSizeId(),
            "",
            state.now(),
            state.currentIdsUser().getIdsUserId()
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
            state.currentIdsUser().getIdsUserId()
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
            state.currentIdsUser().getIdsUserId()
        );
    }

    public static GoogleStaticMap mapToRecord(@NonNull GoogleStaticMapRequest request)
    {
        return new GoogleStaticMap(
            request.sizeX(),
            request.sizeY(),
            request.scale(),
            request.zoom(),
            MapTypeEnum.getByValue(request.mapType()),
            request.centerLat(),
            request.centerLng()
        );
    }

    public static GoogleStaticMapMarker mapToRecord(@NonNull GoogleStaticMapMarkerRequest request)
    {
        return new GoogleStaticMapMarker(
            MarkerSizeEnum.getByValue(request.getSize()),
            MarkerColorEnum.getByValue(request.getColor()),
            request.getLabel(),
            request.getCenterLat(),
            request.getCenterLng()
        );
    }

    public static OwnRequest mapToRecord(@NonNull PostOwnRequestRequest request)
    {
        return new OwnRequest(request.companyId(), request.emailPart());
    }

    public static OwnComplete mapToRecord(@NonNull GetOwnCompleteRequest request)
    {
        return new OwnComplete(request.getCode());
    }
}
