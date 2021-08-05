package com.kbalazsworks.stackjudge.api.controllers.maps_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.maps_requests.GoogleStaticMapsRequest;
import com.kbalazsworks.stackjudge.api.requests.maps_requests.MarkerRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.services.RequestMapperService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.MapsService;
import com.kbalazsworks.stackjudge.domain.value_objects.service_responses.maps_service.StaticProxyResponse;
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

@RestController("MapsProxyAction")
@RequestMapping(MapsConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class StaticProxyAction
{
    private final MapsService mapsService;
    private final StateService stateService;

    @PostMapping(path = MapsConfig.POST_STATIC_PROXY_PATH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<StaticProxyResponse>> action(
        GoogleStaticMapsRequest googleStaticMapsRequest,
        @RequestParam("marker") List<String> marker
    ) throws Exception
    {
        new JavaxValidatorService<GoogleStaticMapsRequest>().validate(googleStaticMapsRequest);

        List<MarkerRequest> markersRequests = Arrays.asList(new ObjectMapper().readValue(
            marker.toString(),
            MarkerRequest[].class
        ));
        markersRequests.forEach(new JavaxValidatorService<MarkerRequest>()::validate);

        return new ResponseEntityBuilder<StaticProxyResponse>()
            .data(mapsService.staticProxy(
                RequestMapperService.mapToRecord(googleStaticMapsRequest, stateService.getState()),
                markersRequests
            ))
            .build();
    }
}
