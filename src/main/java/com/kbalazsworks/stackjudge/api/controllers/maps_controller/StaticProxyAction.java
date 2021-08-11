package com.kbalazsworks.stackjudge.api.controllers.maps_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.maps_requests.GoogleStaticMapRequest;
import com.kbalazsworks.stackjudge.api.requests.maps_requests.GoogleStaticMapMarkerRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.services.RequestMapperService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.MapsService;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.StaticMapResponse;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController("MapsProxyAction")
@RequestMapping(MapsConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class StaticProxyAction
{
    private final MapsService  mapsService;
    private final StateService stateService;

    @PostMapping(path = MapsConfig.POST_STATIC_PROXY_PATH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<StaticMapResponse>> action(
        GoogleStaticMapRequest googleStaticMapRequest,
        @RequestParam("marker") List<String> marker
    ) throws Exception
    {
        new JavaxValidatorService<GoogleStaticMapRequest>().validate(googleStaticMapRequest);

        List<GoogleStaticMapMarkerRequest> markersRequests = Arrays.asList(new ObjectMapper().readValue(
            marker.toString(),
            GoogleStaticMapMarkerRequest[].class
        ));
        markersRequests.forEach(new JavaxValidatorService<GoogleStaticMapMarkerRequest>()::validate);

        return new ResponseEntityBuilder<StaticMapResponse>()
            .data(
                mapsService.staticProxy(
                    RequestMapperService.mapToRecord(googleStaticMapRequest),
                    markersRequests.stream().map(RequestMapperService::mapToRecord).collect(Collectors.toList()),
                    stateService.getState().now()
                )
            )
            .build();
    }
}
