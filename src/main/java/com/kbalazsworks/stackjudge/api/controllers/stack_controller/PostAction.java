package com.kbalazsworks.stackjudge.api.controllers.stack_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.stack_request.StackCreateRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.services.RequestMapperService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.StackService;
import com.kbalazsworks.stackjudge.session.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("StackPostAction")
@RequestMapping(StackConfig.CONTROLLER_URI)
public class PostAction
{
    private StackService   stackService;
    private SessionService sessionService;

    @Autowired
    public void setStackService(StackService stackService)
    {
        this.stackService = stackService;
    }

    @Autowired
    public void setSessionService(SessionService sessionService)
    {
        this.sessionService = sessionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseData<String>> action(@RequestBody StackCreateRequest request) throws Exception
    {
        new JavaxValidatorService<StackCreateRequest>().validate(request);

        stackService.create(RequestMapperService.mapToRecord(request, sessionService.getSessionState()));

        ResponseEntityBuilder<String> responseEntityBuilder = new ResponseEntityBuilder<>();

        responseEntityBuilder.setData(null);

        return responseEntityBuilder.build();
    }
}
