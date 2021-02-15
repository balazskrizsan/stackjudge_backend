package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Accessors(fluent = true)
@Getter
@Setter
public class AddressFakeBuilder
{
    public static final Long defaultId1  = 102001L;
    public static final Long defaultId2  = 102002L;
    public static final Long defaultId3  = 102003L;
    public static final Long defaultId4  = 102004L;
    public static final Long defaultId5  = 102005L;
    public static final Long defaultId6  = 102006L;
    public static final Long defaultId7  = 102007L;
    public static final Long defaultId8  = 102008L;
    public static final Long defaultId9  = 102009L;
    public static final Long defaultId10 = 102010L;

    private Long          id              = defaultId1;
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
}
