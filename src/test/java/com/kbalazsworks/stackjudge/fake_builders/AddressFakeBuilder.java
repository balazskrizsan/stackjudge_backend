package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.entities.Address;

import java.time.LocalDateTime;
import java.util.List;

public class AddressFakeBuilder
{
    public static final Long defaultId1 = 102001L;

    private Long          id              = defaultId1; //156789516L;
    private Long          companyId       = CompanyFakeBuilder.defaultId1;
    private String        fullAddress     = "Full address 1, 123, 4";
    private Double        markerLat       = 11.11;
    private Double        markerLng       = 22.22;
    private Double        manualMarkerLat = 33.33;
    private Double        manualMarkerLng = 44.44;
    private LocalDateTime createdAt       = LocalDateTime.of(2020, 11, 22, 11, 22, 33);
    private Long          createdBy       = 333L;

    public List<Address> buildAsList()
    {
        return List.of(build());
    }

    public Address build()
    {
        return new Address(
            id,
            companyId,
            fullAddress,
            markerLat,
            markerLng,
            manualMarkerLat,
            manualMarkerLng,
            createdAt,
            createdBy
        );
    }

    public AddressFakeBuilder setId(Long id)
    {
        this.id = id;

        return this;
    }

    public AddressFakeBuilder setCompanyId(Long companyId)
    {
        this.companyId = companyId;

        return this;
    }

    public AddressFakeBuilder setFullAddress(String fullAddress)
    {
        this.fullAddress = fullAddress;

        return this;
    }

    public AddressFakeBuilder setMarkerLat(Double markerLat)
    {
        this.markerLat = markerLat;

        return this;
    }

    public AddressFakeBuilder setMarkerLng(Double markerLng)
    {
        this.markerLng = markerLng;

        return this;
    }

    public AddressFakeBuilder setManualMarkerLat(Double manualMarkerLat)
    {
        this.manualMarkerLat = manualMarkerLat;

        return this;
    }

    public AddressFakeBuilder setManualMarkerLng(Double manualMarkerLng)
    {
        this.manualMarkerLng = manualMarkerLng;

        return this;
    }

    public AddressFakeBuilder setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;

        return this;
    }

    public AddressFakeBuilder setCreatedBy(Long createdBy)
    {
        this.createdBy = createdBy;

        return this;
    }
}
