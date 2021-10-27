package com.kbalazsworks.stackjudge.domain.map_module.services;

import com.kbalazsworks.stackjudge.domain.address_module.entities.Address;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapSizeEnum;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MarkerColorEnum;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MarkerSizeEnum;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMapMarker;
import org.springframework.stereotype.Service;

@Service
public class MapMapperService
{
    public GoogleStaticMapMarker mapAddressToGoogleStaticMapMarker(Address address)
    {
        return new GoogleStaticMapMarker(
            MarkerSizeEnum.MID,
            MarkerColorEnum.RED,
            "",
            address.markerLat(),
            address.markerLng()
        );
    }

    public GoogleStaticMap mapAddressToGoogleStaticMap(Address address, MapSizeEnum mapSizeEnum)
    {
        return new GoogleStaticMap(
            mapSizeEnum.getX(),
            mapSizeEnum.getY(),
            mapSizeEnum.getScale(),
            mapSizeEnum.getZoom(),
            mapSizeEnum.getMapType(),
            address.markerLat(),
            address.markerLng()
        );
    }
}
