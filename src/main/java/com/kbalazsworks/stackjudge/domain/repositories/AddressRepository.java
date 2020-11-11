package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.session.entities.SessionState;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.Address addressTable =
        com.kbalazsworks.stackjudge.db.tables.Address.ADDRESS;

    public void create(Address address)
    {
        createQueryBuilder()
            .insertInto(
                addressTable,
                addressTable.COMPANY_ID,
                addressTable.FULL_ADDRESS,
                addressTable.MARKER_LAT,
                addressTable.MARKER_LNG,
                addressTable.MANUAL_MARKER_LAT,
                addressTable.MANUAL_MARKER_LNG,
                addressTable.CREATED_AT,
                addressTable.CREATED_BY
            )
            .values(
                address.companyId(),
                address.fullAddress(),
                address.markerLat(),
                address.markerLng(),
                address.manualMarkerLat(),
                address.manualMarkerLng(),
                address.createdAt(),
                address.createdBy()
            )
            .execute();
    }
}
