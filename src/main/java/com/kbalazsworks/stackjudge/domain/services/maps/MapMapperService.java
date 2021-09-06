package com.kbalazsworks.stackjudge.domain.services.maps;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapSizeEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MarkerColorEnum;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MarkerSizeEnum;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMapMarker;
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
