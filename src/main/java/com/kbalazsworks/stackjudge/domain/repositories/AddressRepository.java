package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.aspect_enums.RedisCacheRepositorieEnum;
import com.kbalazsworks.stackjudge.domain.aspects.RedisCacheByCompanyIdList;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.Address addressTable =
        com.kbalazsworks.stackjudge.db.tables.Address.ADDRESS;

    public void create(@NonNull Address address)
    {
        getQueryBuilder()
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

    @RedisCacheByCompanyIdList(repository = RedisCacheRepositorieEnum.ADDRESS)
    public List<Address> search(List<Long> companyIds)
    {
        return getQueryBuilder()
            .selectFrom(addressTable)
            .where(addressTable.COMPANY_ID.in(companyIds))
            .fetchInto(Address.class);
    }
}
