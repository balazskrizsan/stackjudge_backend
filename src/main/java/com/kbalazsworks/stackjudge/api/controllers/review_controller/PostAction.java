package com.kbalazsworks.stackjudge.api.controllers.review_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.requests.review_requests.ReviewCreateRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.services.RequestMapperService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.ReviewService;
import com.kbalazsworks.stackjudge.session.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("ReviewPostAction")
@RequestMapping(ReviewConfig.CONTROLLER_URI)
public class PostAction
{
    private ReviewService  reviewService;
    private SessionService sessionService;

    @Autowired
    public void setReviewService(ReviewService reviewService)
    {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setSessionService(SessionService sessionService)
    {
        this.sessionService = sessionService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<String>> action(ReviewCreateRequest request) throws ApiException
    {
        new JavaxValidatorService<ReviewCreateRequest>().validate(request);

        reviewService.create(RequestMapperService.mapToRecord(request, sessionService.getSessionState()));

        return new ResponseEntityBuilder<String>().data(null).build();
    }
}
