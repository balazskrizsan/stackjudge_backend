package com.kbalazsworks.stackjudge.api.controllers.review_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.requests.review_requests.ReviewCreateRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.services.RequestMapperService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.ReviewService;
import com.kbalazsworks.stackjudge.state.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ReviewPostAction")
@RequestMapping(ReviewConfig.CONTROLLER_URI)
public class PostAction
{
    private ReviewService reviewService;
    private StateService  stateService;

    @Autowired
    public void setReviewService(ReviewService reviewService)
    {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setStateService(StateService stateService)
    {
        this.stateService = stateService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<String>> action(ReviewCreateRequest request) throws ApiException
    {
        new JavaxValidatorService<ReviewCreateRequest>().validate(request);

        reviewService.create(RequestMapperService.mapToRecord(request, stateService.getState()));

        return new ResponseEntityBuilder<String>().data(null).build();
    }
}
