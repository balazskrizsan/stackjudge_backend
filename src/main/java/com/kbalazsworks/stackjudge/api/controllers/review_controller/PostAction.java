package com.kbalazsworks.stackjudge.api.controllers.review_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.requests.review_requests.ReviewCreateRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.services.RequestMapperService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.review_module.services.ReviewService;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ReviewPostAction")
@RequestMapping(ReviewConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class PostAction
{
    private final ReviewService reviewService;
    private final StateService  stateService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<String>> action(ReviewCreateRequest request) throws ApiException
    {
        new JavaxValidatorService<ReviewCreateRequest>().validate(request);

        reviewService.create(RequestMapperService.mapToRecord(request, stateService.getState()));

        return new ResponseEntityBuilder<String>().data(null).build();
    }
}
